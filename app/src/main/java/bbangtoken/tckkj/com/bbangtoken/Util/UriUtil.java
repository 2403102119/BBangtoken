package bbangtoken.tckkj.com.bbangtoken.Util;

/**
 *
 * kylin on 2017/12/12.
 */

public class UriUtil {


    /*ip获取*/
    public static final String host_ip = MD5Utils.getHostIP();


    public  static final String ip="http://qb.tangchaoke.com/";





    //登录
    public static final String ip_login = ip+"api/user_login";
    //验证验证码
    public static final String ip_verify = ip+"api/check_verify";
    //获取验证码
    public static final String ip_loanpage = ip+"api/send_verify";
    //注册
    public static final String ip_register = ip+"api/user_register";
    //验证助记词
    public static final String ip_mnemonic_words = ip+"api/check_auxiliaries";
    //忘记密码发送短信
    public static final String ip_slip = ip+"api/send_forget_verify";
    //首页数据
    public static final String ip_home = ip+"api/index_data_list";
    //资讯页面
    public static final String ip_message = ip+"api/note_news_list";
    //资讯详情页面
    public static final String ip_message_item = ip+"api/note_news_detail";
    //常用地址溥（钱包地址）
    public static final String ip_site = ip+"api/bag_site_list";
    //我的好友
    public static final String ip_friend = ip+"api/user_friends";
    //帮助中心
    public static final String ip_help = ip+"api/help";
    //开启智能狗
    public static final String ip_open = ip+"api/open_intellect";
    //关闭智能狗
    public static final String ip_close = ip+"api/close_intellect";
    //币融借款
    public static final String ip_borrow = ip+"api/borrow_currency";
    //币融还款
    public static final String ip_refound = ip+"api/repay_currency";
    //币融列表
    public static final String ip_coin_melts= ip+"api/currency_fuse_list";
    //我的收益
    public static final String ip_earnings= ip+"api/user_profit";
    //智能收益
    public static final String ip_capacity_earnings= ip+"api/intellect_profit_list";
    //推广收益
    public static final String ip_generalize_earnings= ip+"api/extend_profit_list";
    //导出私钥
    public static final String ip_derive= ip+"api/cat_p_key";
    //修改个人信息
    public static final String ip_user_info= ip+"api/modify_user_info";
    //转账通知
    public static final String ip_transfer_accounts= ip+"api/transfer_notice";
    //系统消息
    public static final String ip_system= ip+"api/sys_news_list";
    //系统消息详情
    public static final String ip_system_item= ip+"api/sys_news_detail";
    //个人信息
    public static final String ip_personal_details= ip+"api/user_info";
    //转入转出记录
    public static final String ip_record= ip+"api/turn_into_list";
    //兑换
    public static final String ip_conversion = ip+"api/exchange";
    //兑换记录
    public static final String ip_conversion_item = ip+"api/exchange_list";
    //添加钱包地址
    public static final String ip_add_money = ip+"api/add_wallet_address";
    //智能狗启动记录
    public static final String ip_start_the_recording = ip+"api/intellect_record";
    //币种排序
    public static final String ip_sort = ip+"api/quotation_sort";
    //币种排序列表
    public static final String ip_sort_list = ip+"api/quotation_list";
    //币种搜索
    public static final String ip_select_currency = ip+"api/select_currency";
    //添加行情
    public static final String ip_add_quotation = ip+"api/quotation_list";
    //选择区号
    public static final String ip_Select_code = ip+"api/area_code";
    //二维码
    public static final String ip_shift_to = ip+"api/get_qrcode";
    //转出
    public static final String ip_rool_out = ip+"api/turn_out";
    //修改密码发送验证码
    public static final String ip_amend = ip+"api/send_modify_verify";
    //修改密码发送验证码
    public static final String ip_change_password = ip+"api/user_modify_password";
    //设置新密码
    public static final String ip_set_password = ip+"api/repeat_password";
    //智能狗列表
    public static final String ip_smart_dog_list = ip+"api/intellect_list";
    //推广邀请
    public static final String ip_invite = ip+"api/user_invite";
    //关于我们
    public static final String ip_about = ip+"api/about_us";
    //币种列表
    public static final String ip_curr = ip+"api/currency_list";
    //语言切换
    public static final String ip_language = ip+"api/change_language";
    //借款周期
    public static final String ip_cycle = ip+"api/periodic_list";
    //更新钱包地址
    public static final String ip_update = ip+"api/create_wallet_address";
    //上传头像
    public static final String ip_uploding = ip+"api/user_header_info";
    //推广邀请二维码
    public static final String ip_extension = ip+"api/extension_qrcode";
    //设置收款金额
    public static final String ip_qrcode = ip+"api/get_qrcode_amount";
    //币种介绍
    public static final String ip_introduction = ip+"api/currency_brief_introduction";




    public final static String account = "userPhone";
    public final static String pwd = "userPwd";
    public  static String wechat = "";


}
