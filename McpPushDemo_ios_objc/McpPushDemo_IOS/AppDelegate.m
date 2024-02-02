//
//  AppDelegate.m
//  McpPushDemo_IOS
//
//  Created by 김남훈 on 1/15/24.
//

#import "AppDelegate.h"
@import Evergage;


@interface AppDelegate ()

@property (nonatomic, strong) Evergage *evergage;

@end

@implementation AppDelegate



//SDK 초기화
- (void)configureMcpSDK {
    self.evergage = [Evergage sharedInstance];
    
#ifdef DEBUG
    self.evergage.logLevel = EVGLogLevelWarn;
#endif
    
    //테스트용 정보 설정 코드
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    NSString *userId = [userDefaults objectForKey:@"PREFERENCE_KEY_USER_ID"];
    if (userId == nil) {
        [userDefaults setObject:@"gp_nhkim" forKey:@"PREFERENCE_KEY_USER_ID"];
        [userDefaults synchronize];
    }
    
    NSString *account = [userDefaults objectForKey:@"PREFERENCE_KEY_ACCOUNT"];
    if (account == nil) {
        [userDefaults setObject:@"GoldenPlanet" forKey:@"PREFERENCE_KEY_ACCOUNT"];
        [userDefaults synchronize];
    }
    
    NSString *dataset = [userDefaults objectForKey:@"PREFERENCE_KEY_DATASET"];
    if (dataset == nil) {
        [userDefaults setObject:@"gp_test" forKey:@"PREFERENCE_KEY_DATASET"];
        [userDefaults synchronize];
    }
    
    //    아래 설정코드만으로 자동 추적되는 이벤트들 :
    //    App events (launch, install, upgrade, foreground, background, and so on)
    //    Miscellaneous SDK-handled tracking and events: campaigns (In-App), view time. accumulation, any APIs reduced by swizzling, and so on
    
    [self.evergage setUserId: userId != nil ? userId : @"gp_nhkim"];
    
    [self.evergage startWithClientConfiguration:^(EVGClientConfigurationBuilder * _Nonnull builder) {
        builder.account = account != nil ? account : @"gp_nhkim";
        builder.dataset = dataset != nil ? dataset : @"gp_test";
        builder.usePushNotifications = TRUE;
    }];
}

- (void) resetMcpSDK {
    [self.evergage reset];
    //테스트용 정보 설정 코드
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    NSString *userId = [userDefaults objectForKey:@"PREFERENCE_KEY_USER_ID"];
    if (userId == nil) {
        [userDefaults setObject:@"gp_nhkim" forKey:@"PREFERENCE_KEY_USER_ID"];
        [userDefaults synchronize];
    }
    
    NSString *account = [userDefaults objectForKey:@"PREFERENCE_KEY_ACCOUNT"];
    if (account == nil) {
        [userDefaults setObject:@"GoldenPlanet" forKey:@"PREFERENCE_KEY_ACCOUNT"];
        [userDefaults synchronize];
    }
    
    NSString *dataset = [userDefaults objectForKey:@"PREFERENCE_KEY_DATASET"];
    if (dataset == nil) {
        [userDefaults setObject:@"gp_test" forKey:@"PREFERENCE_KEY_DATASET"];
        [userDefaults synchronize];
    }
    
    //    아래 설정코드만으로 자동 추적되는 이벤트들 :
    //    App events (launch, install, upgrade, foreground, background, and so on)
    //    Miscellaneous SDK-handled tracking and events: campaigns (In-App), view time. accumulation, any APIs reduced by swizzling, and so on
    
    [self.evergage setUserId: userId != nil ? userId : @"gp_nhkim"];
    
    [self.evergage startWithClientConfiguration:^(EVGClientConfigurationBuilder * _Nonnull builder) {
        builder.account = account != nil ? account : @"gp_nhkim";
        builder.dataset = dataset != nil ? dataset : @"gp_test";
        builder.usePushNotifications = TRUE;
    }];
    
}

- (BOOL)application:(UIApplication *)application willFinishLaunchingWithOptions:(NSDictionary<UIApplicationLaunchOptionsKey,id> *)launchOptions {
    NSLog(@"willFinishLaunchingWithOptions");
    [FIRApp configure];
    [FIRMessaging messaging].delegate = self;
    
    [self configureMcpSDK];
    return YES;
}


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    NSLog(@"didFinishLaunchingWithOptions");
    // Override point for customization after application launch.
    
    [self registerNoti];
    
    
    return YES;
}

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    const unsigned char *dataBuffer = (const unsigned char *)deviceToken.bytes;
    NSMutableString *hexString  = [NSMutableString stringWithCapacity:(deviceToken.length * 2)];
    for (int i = 0; i < deviceToken.length; ++i) {
        [hexString appendFormat:@"%02x", dataBuffer[i]];
    }
    NSString *result = [hexString copy];
    NSLog(@"[didRegisterForRemoteNotificationsWithDeviceToken] deviceToken : %@", result);
    
    [FIRMessaging messaging].APNSToken = deviceToken;
   
}


- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {
    NSLog(@"didFailToRegisterForRemoteNotificationsWithError");
}


- (void)messaging:(FIRMessaging *)messaging didReceiveRegistrationToken:(NSString *)fcmToken {
    NSLog(@"[didReceiveRegistrationToken] fcmToken : %@", fcmToken);
    // 어플리케이션 서버에 토큰을 보낼 경우
    
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler {
    NSLog(@"[didReceiveRemoteNotification] userInfo : %@", userInfo);
    [[FIRMessaging messaging] appDidReceiveMessage:userInfo];
    completionHandler(UIBackgroundFetchResultNewData);
}

- (void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions))completionHandler {
    NSDictionary *userInfo = notification.request.content.userInfo;
    NSLog(@"[willPresentNotification] userInfo : %@", userInfo);
    [[FIRMessaging messaging] appDidReceiveMessage:userInfo];
    //    푸시받았을 때
    completionHandler(UNNotificationPresentationOptionBadge | UNNotificationPresentationOptionAlert | UNNotificationPresentationOptionSound);
}


- (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)(void))completionHandler {
//  푸시 탭할때
    NSDictionary *userInfo = response.notification.request.content.userInfo;
    NSLog(@"[didReceiveNotificationResponse] userInfo : %@", userInfo);
//    [[FIRMessaging messaging] appDidReceiveMessage:userInfo];
    
    completionHandler();
}


#pragma mark - Push Setting
- (void)registerNoti {
    if([UNUserNotificationCenter class] != nil) {
        [UNUserNotificationCenter currentNotificationCenter].delegate = self;
        UNAuthorizationOptions authOptions = UNAuthorizationOptionAlert | UNAuthorizationOptionSound | UNAuthorizationOptionBadge;
        [[UNUserNotificationCenter currentNotificationCenter] requestAuthorizationWithOptions:authOptions completionHandler:^(BOOL granted, NSError * _Nullable error) {
            if (!error) {
                if (granted) {
                    NSLog(@"Notification granted");
                }
            }
        }];
        
    }
    
    [[UIApplication sharedApplication] registerForRemoteNotifications];
}



#pragma mark - UISceneSession lifecycle


- (UISceneConfiguration *)application:(UIApplication *)application configurationForConnectingSceneSession:(UISceneSession *)connectingSceneSession options:(UISceneConnectionOptions *)options {
    // Called when a new scene session is being created.
    // Use this method to select a configuration to create the new scene with.
    return [[UISceneConfiguration alloc] initWithName:@"Default Configuration" sessionRole:connectingSceneSession.role];
}


- (void)application:(UIApplication *)application didDiscardSceneSessions:(NSSet<UISceneSession *> *)sceneSessions {
    // Called when the user discards a scene session.
    // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
    // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
}


@end
