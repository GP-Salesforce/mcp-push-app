//
//  ViewController.m
//  McpPushDemo_IOS
//
//  Created by 김남훈 on 1/15/24.
//

#import "ViewController.h"
#import "AppDelegate.h"

//==> ViewController는 테스트를 위한 화면을 제공할뿐 실제 SDK 사용과 거리가 있으니 참고

@interface ViewController ()
@property (weak, nonatomic) IBOutlet UITextField *etUserID;
@property (weak, nonatomic) IBOutlet UITextField *etAccount;
@property (weak, nonatomic) IBOutlet UITextField *etDataSet;
@property (weak, nonatomic) IBOutlet UILabel *tvVersion;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)viewWillAppear:(BOOL)animated {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    
    NSString *userId = [userDefaults objectForKey:@"PREFERENCE_KEY_USER_ID"];
    if (userId == nil) {
        self.etUserID.text = @"gp_nhkim";
        [userDefaults setObject:@"gp_nhkim" forKey:@"PREFERENCE_KEY_USER_ID"];
        [userDefaults synchronize];
    } else {
        self.etUserID.text = userId;
    }
                                
    NSString *account = [userDefaults objectForKey:@"PREFERENCE_KEY_ACCOUNT"];
    if (account == nil) {
        self.etAccount.text = @"GoldenPlanet";
        [userDefaults setObject:@"GoldenPlanet" forKey:@"PREFERENCE_KEY_ACCOUNT"];
        [userDefaults synchronize];
    } else {
        self.etAccount.text = account;
    }
    
    NSString *dataset = [userDefaults objectForKey:@"PREFERENCE_KEY_DATASET"];
    if (dataset == nil) {
        self.etDataSet.text = @"gp_test";
        [userDefaults setObject:@"gp_test" forKey:@"PREFERENCE_KEY_DATASET"];
        [userDefaults synchronize];
    } else {
        self.etDataSet.text = dataset;
    }
    

    //빌드 / 버전 표시
    
    NSString *version = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleShortVersionString"];
    NSString *build = [[[NSBundle mainBundle] infoDictionary] objectForKey:(NSString *)kCFBundleVersionKey];
    self.tvVersion.text = [NSString stringWithFormat:@"%@ / %@", build, version];
    
    
    
    
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event {
    [self.view endEditing:YES];
}



- (IBAction)btnSave:(id)sender {
    NSLog(@"btnSave");
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"안내"
                                                                             message:@"설정값이 MCP SDK로 반영됩니다."
                                                                      preferredStyle:UIAlertControllerStyleAlert];
    
    UIAlertAction *okAction = [UIAlertAction actionWithTitle:@"OK"
                                                       style:UIAlertActionStyleDefault
                                                     handler:^(UIAlertAction *action){
        [self saveData];
        //AOS와 달리 IOS는 코드 레벨에서 앱을 재시작시킬수 없음
        [(AppDelegate *)[UIApplication sharedApplication].delegate resetMcpSDK];
//        [(AppDelegate *)[UIApplication sharedApplication].delegate configureMcpSDK];
    }];
    
    [alertController addAction:okAction];
    
    [self presentViewController:alertController animated:YES completion:nil];
}

- (void)saveData {
    NSUserDefaults *userDefaults = [NSUserDefaults standardUserDefaults];
    [userDefaults setObject:self.etUserID.text forKey:@"PREFERENCE_KEY_USER_ID"];
    [userDefaults setObject:self.etAccount.text forKey:@"PREFERENCE_KEY_ACCOUNT"];
    [userDefaults setObject:self.etDataSet.text forKey:@"PREFERENCE_KEY_DATASET"];
    [userDefaults synchronize];
    NSLog(@"saveData");
}


@end
