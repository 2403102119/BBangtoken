package bbangtoken.tckkj.com.bbangtoken.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * kylin on 2018/6/25.
 */

public class Define {
    public String flag;
    public String message;
    public String error_code;
    public float handle_time;

    //登录model类
    public static class LoginModel{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public List<loginlist>data ;
    }
    //登录model二级类
    public static class loginlist{
        public String u_account;
        public String u_header;
        public int state;
        public String token;
        public int user_id;
    }
    //选择区号model类
    public static class SelectModel{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public List<selectlistmodel> data;
    }
    public static class selectlistmodel{
        public String name;
        public String code;
    }
    //选择区号model类
    public static class RegistModel{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public List<registlistmodel> data;
    }
    public static class registlistmodel{
        public String auxiliaries;
    }
    //首页
    public static class HomeModel{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public homelist data;
    }
    public static class homelist{
        public List<currency_list1> currency_list;
        public List<note_news1> note_news;
    }
    public static class currency_list1{
        public String balance;
        public String currency_name;
        public String currency_icon;
        public int currency_id;
    }
    public static class note_news1{
        public int id;
        public String title;
    }


    //转入转出记录model类
    public static class ShiftModel{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public shiftlistmodel data;
    }
    public static class shiftlistmodel{
        public List<shift>result;
        public double rate;

    }
    public static class shift{
        public int user_id;
        public int transfer_type;
        public int currency_id;
        public String amount;
        public String payment_address;
        public String recive_address;
        public int state;
        public long ctime;
        public String currency_name;
    }

    //
//    //兑换记录model类
//    public static class  Record{
//        public String flag;
//        public String message;
//        public String error_code;
//        public float handle_time;
//        public recordmodel data;
//    }
//    public static class recordmodel{
//        public record record1;
//        public double rate;
//
//    }
//    public static class record{
//        public int id;
//        public int user_id;
//        public int currency_id;
//        public int currency_target_id;
//        public String amount;
//        public String exchange_amount;
//        public long ctime;
//        public String currency_name;
//    }
    //资讯model类
    public static class More{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public List<moreitem> data;
    }
    public static class moreitem{
        public  int id;
        public  String title;
        public  String thumbnail;
        public  long ctime;
    }
    //资讯详情类
    public static class Moredetail{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public moredetailitem data;
    }
    public static class moredetailitem{
        public  String title;
        public  String content;
        public  long ctime;
    }

    //我的页面类
    public static class me{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public meitem data;
    }
    public static class meitem{
        public  String u_header;
        public  String nick_name;
        public  long aleady_borrow_currency;
        public  int intelligence_currency;
        public  int unread;
    }

    //币融列表页面
    public static class Coin_melts{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public coin_meltsitem data;
    }
    public static class coin_meltsitem{
        public  List<coin_list> fuse_list;
        public double total_borrow_currency;
        public double total_borrow_num;
        public double total_wait_repay_amount;

    }
    public static class coin_list{
        public int user_id;
        public int currency_id;
        public String may_borrow_amount;
        public String already_borrow_amount;
        public String already_repay_amount;
        public String wait_repay_amount;
        public long expire_time;
        public int state;
        public String rate;
        public long ctime;
        public String currency_name;
        public String currency_icon;
    }
    //智能狗列表页面
    public static class Smart_dog{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public  List<Smart_dog_item> data;
    }
    public static class Smart_dog_item{

        public int currency_id;
        public String currency_name;
        public String currency_icon;
        public int state;
        public float principal_amount;

    }

    //智能狗启动记录页面
    public static class Start_dog{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public  List<Start_dog_item> data;
    }
    public static class Start_dog_item{

        public float append_amount;
        public String currency_name;
        public String currency_icon;
        public long ctime;

    }
    //开启智能狗页面
    public static class dog{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public  List<dog_item> data;
    }
    public static class dog_item{

    }

    //开启智能狗页面
    public static class site{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public  List<site_item> data;
    }
    public static class site_item{
        public String currency_name;
        public String currency_icon;
        public int wallet_id;
        public String wallet_address;
        public String bag_name;


    }
    //我的好友页面
    public static class Friend{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public  friend_item data;
    }
    public static class friend_item{
        public List<friend_item_one> list;
        public int total_friend;

    }
    public static class friend_item_one{
        public String nick_name;
        public String u_header;
        public String u_account;
        public String c_time;
        public String state;
    }
    //系统消息页面
    public static class System{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public  List<system_item> data;
    }
    public static class system_item{
        public String title;
        public String ctime;
        public int id;
    }
    //系统消息详情页面
    public static class System_detail{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public system_detail_item data;
    }
    public static class system_detail_item{
        public String title;
        public String ctime;
        public String content;
    }
    //推广邀请页面
    public static class Invite{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public invite_item data;
    }
    public static class invite_item{
        public String u_account;
        public String nick_name;
        public String u_header;
        public String code;
    }
    //我的收益页面
    public static class My_income{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public My_income_item data;
    }
    public static class My_income_item{
        public String total_intellect_profit;
        public String today_extend_profit;
        public String today_intellect_profit;
    }

    //智能收益页面
    public static class Smart_earnings{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public  List<Smart_earnings_item> data;
    }
    public static class Smart_earnings_item{
        public String currency_name;
        public String currency_icon;
        public String  ctime;
        public float  amount;
    }
    //推广收益页面
    public static class Promotion_earnings{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public  List<Promotion_earnings_item> data;
    }
    public static class Promotion_earnings_item{
        public String nick_name;
        public String u_account;
        public String u_header;
        public String ctime;
        public float amount;
    }
    //帮助中心页面
    public static class help_center{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public  List<help_center_item> data;
    }
    public static class help_center_item {
        public String title_name;
        public String content;
    }
    //验证助记词
    public static class Verify_mnemonic_words {
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public Verify_mnemonic_words_item data;
    }

    public static class Verify_mnemonic_words_item {
        public String p_key;

    }
    //验证助记词
    public static class About {
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public about_item data;
    }

    public static class about_item {
        public String about_we;
        public String project_logo;
        public String version_information;

    }
    //验证助记词
    public static class seek {
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public seek_item data;
    }

    public static class seek_item {
        public int currency_id;
        public String currency_name;
        public String currency_icon;

    }
    //币种排序列表
    public static class Sort {
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public List<Sort_item> data;
    }

    public static class Sort_item {
        public int currency_id;
        public String currency_name;
        public String currency_icon;

    }
    //借款周期列表
    public static class Cycle {
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public String user_id;
        public List<Cycle_item> data;
    }

    public static class Cycle_item {
        public int id;
        public String cycle;
        public String cycle_name;
        public float interest_rate;

    }
    //生成二维码
    public static class Twocode {
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public String user_id;
        public List<Twocode_item> data;
    }

    public static class Twocode_item {
        public String address;
        public String url;
        public String currency_name;
        public float balance;

    }

    //头像
    public static class touxiang {
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public String user_id;
        public String data;
    }

    //推广邀请
    public static class generalize{
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public String user_id;
        public generalize_item data;
    }
    public static class generalize_item{
        public int id;
        public String u_account;
        public String nick_name;
        public String u_header;
        public String code;
        public String url;
        public String qrcode;
    }

    //行情列表
    public static class Market {
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public String user_id;
        public List<Market_item> data;
    }

    public static class Market_item {
        public int currency_id;
        public String currency_name;
        public String currency_icon;

    }
    //币种介绍
    public static class introduce {
        public String flag;
        public String message;
        public String error_code;
        public float handle_time;
        public String user_id;
        public introduce_item data;
    }

    public static class introduce_item {
        public int currency_id;
        public String currency_name;
        public String currency_icon;
        public String currency_account;
        public String market_rank;
        public String market_value;
        public String circul_num;
        public String issue_num;
        public String funding_cost;
        public String issue_time;

    }

    public static class commonResponseEntity {

        public String ch;
        public long ts;
        public tick_item tick;
        public exchange_rate_item exchange_rate;

        public static class tick_item {
            public String id;
            public float low;
            public float high;
            public float open;
            public float close;
            public float vol;
            public float amount;
            public float version;
            public float count;
        }

        public static class exchange_rate_item {
            public long data_time;
            public String name;
            public float rate;
            public long time;
        }

    }
    }
