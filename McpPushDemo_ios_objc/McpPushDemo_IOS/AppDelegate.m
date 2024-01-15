//
//  AppDelegate.m
//  McpPushDemo_IOS
//
//  Created by 김남훈 on 1/15/24.
//

#import "AppDelegate.h"
@import Evergage;
@import FirebaseCore;

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

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    [FIRApp configure];
    
    [self configureMcpSDK];
    
    return YES;
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
