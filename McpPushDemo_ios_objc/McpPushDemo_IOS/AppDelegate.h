//
//  AppDelegate.h
//  McpPushDemo_IOS
//
//  Created by 김남훈 on 1/15/24.
//

#import <UIKit/UIKit.h>
@import UserNotifications;

@interface AppDelegate : UIResponder <UIApplicationDelegate, UNUserNotificationCenterDelegate>


- (void)configureMcpSDK;

@end

