//
//  AppDelegate.h
//  McpPushDemo_IOS
//
//  Created by 김남훈 on 1/15/24.
//

#import <UIKit/UIKit.h>
@import UserNotifications;
@import FirebaseCore;
@import FirebaseMessaging;

@interface AppDelegate : UIResponder <UIApplicationDelegate, UNUserNotificationCenterDelegate, FIRMessagingDelegate>


- (void)configureMcpSDK;

@end

