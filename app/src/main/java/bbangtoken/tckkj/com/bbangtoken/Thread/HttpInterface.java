package bbangtoken.tckkj.com.bbangtoken.Thread;

import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import bbangtoken.tckkj.com.bbangtoken.Util.UriUtil;


/**
 * kylin on 2017/12/12.
 */

public class HttpInterface {

    private Context context;

    public LoadingDialog loadingDialog;

    public HttpInterface(Context context) {
        this.context = context;
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
            loadingDialog.setCancelable(true);
        }
    }
    //登录
    public void login(String phoneStr,String pwdStr,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_login);
        try{
            userClient.AddParam("u_account",phoneStr);
            userClient.AddParam("u_password",pwdStr);
            loadingDialog.setCancelable(true);
//            App.isLogined = false;
            OkHttpUtils.getInstance().cancelTag("ALL");
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }




    //获取验证码
    public void getCode(String account,String send_type,String secret_key,String send_ip,MApiResultCallback callback){
      /*获取申请贷款页内容*/
        UserClient userClient = new UserClient(UriUtil.ip_loanpage);
        try{
            loadingDialog.setCancelable(false);
            userClient.AddParam("account",account);
            userClient.AddParam("send_class","user_register");
            userClient.AddParam("send_type",send_type);
            userClient.AddParam("secret_key",secret_key);
            userClient.AddParam("send_ip",send_ip);
            userClient.executePost(callback,null,context);
        }catch (Exception e){

        }
    }

    //验证验证码
    public void verify (String p_key,String verify_code,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_verify);
        try{
            loadingDialog.setCancelable(false);
            userClient.AddParam("send_class","user_forget");
            userClient.AddParam("p_key",p_key);
            userClient.AddParam("verify_code",verify_code);
            userClient.executePost(callback,null,context);
        }catch (Exception e){

        }
    }





    //注册
    public void registir(String u_account,String u_password,String verify_code,String t_password,String code,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_register);
        try{
            userClient.AddParam("u_account",u_account);
            userClient.AddParam("u_password",u_password);
            userClient.AddParam("verify_code",verify_code);
            userClient.AddParam("t_password",t_password);
            userClient.AddParam("code",code);

            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }

    //验证助记词
    public void mnemonic_words(String auxiliaries,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_mnemonic_words);
        try{
            userClient.AddParam("auxiliaries",auxiliaries);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }

    //忘记密码发送短信
    public void slip(String send_ip,String p_key,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_slip);
        try{
            userClient.AddParam("send_class","user_forget");
            userClient.AddParam("send_ip",send_ip);
            userClient.AddParam("p_key",p_key);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }

    //首页数据展示
    public void home_page(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_home);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //资讯数据展示
    public void home_message(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_message);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //资讯详情数据展示
    public void ip_message_item(String id,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_message_item);
        try{
            userClient.AddParam("id",id);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //钱包地址
    public void site(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_site);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //我的好友
    public void friend(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_friend);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //帮助中心
    public void help(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_help);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //开启智能狗
    public void open(String currency_id,String amount,String t_password,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_open);
        try{
            userClient.AddParam("currency_id",currency_id);
            userClient.AddParam("amount",amount);
            userClient.AddParam("t_password",t_password);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }

    //关闭智能狗
    public void close_item(String currency_id,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_close);
        try{
            userClient.AddParam("currency_id",currency_id);

            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //币融借款
    public void borrow_money(String currency_id,String borrow_amount,String periodic_id,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_borrow);
        try{
            userClient.AddParam("currency_id",currency_id);
            userClient.AddParam("borrow_amount",borrow_amount);
            userClient.AddParam("periodic_id",periodic_id);

            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //币融还款
    public void refound_money(String currency_id,String repay_amount,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_refound);
        try{
            userClient.AddParam("currency_id",currency_id);
            userClient.AddParam("repay_amount",repay_amount);

            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //币融列表
    public void coin_melts(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_coin_melts);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //我的收益
    public void earnings(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_earnings);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //智能收益
    public void capacity_earnings(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_capacity_earnings);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //推广收益
    public void generalize_earnings(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_generalize_earnings);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //导出私钥
    public void derive(String t_password,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_derive);
        try{
            userClient.AddParam("t_password",t_password);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //修改个人信息
    public void user_info(String nick_name,String u_header,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_user_info);
        try{
            userClient.AddParam("nick_name",nick_name);
            userClient.AddParam("u_header",u_header);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //转账通知
    public void transfer_accounts(String user_id,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_transfer_accounts);
        try{
            userClient.AddParam("user_id",user_id);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //系统消息
    public void system(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_system);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //系统消息详情
    public void system_item(String id,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_system_item);
        try{
            userClient.AddParam("id",id);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //个人信息
    public void personal_details(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_personal_details);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //币种转入转出记录
    public void record(String currency_id,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_record);
        try{
            userClient.AddParam("currency_id",currency_id);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //兑换
    public void conversion(String from_currency_id,String to_currency_id,String amount,String exchange_amount,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_conversion);
        try{
            userClient.AddParam("from_currency_id",from_currency_id);
            userClient.AddParam("to_currency_id",to_currency_id);
            userClient.AddParam("amount",amount);
            userClient.AddParam("exchange_amount",exchange_amount);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    // 兑换记录
    public void conversion_item(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_conversion_item);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //添加钱包地址
    public void add_money(String currency_id,String address,String t_password,String bag_name,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_add_money);
        try{
            userClient.AddParam("currency_id",currency_id);
            userClient.AddParam("address",address);
            userClient.AddParam("t_password",t_password);
            userClient.AddParam("bag_name",bag_name);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //智能狗启动记录
    public void start_the_recording(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_start_the_recording);
        try{

            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //币种排序
    public void sort(String currency_id,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_sort);
        try{
            userClient.AddParam("currency_id",currency_id);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //币种搜索
    public void select_currency(String currency_name,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_select_currency);
        try{
            userClient.AddParam("currency_name",currency_name);

            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //行情列表排序
    public void add_quotation(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_add_quotation);
        try{

            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //选择区号
    public void select_code(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_Select_code);
        try{

            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //转入二维码
    public void shift_to(String currency_id,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_shift_to);
        try{
            userClient.AddParam("currency_id",currency_id);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //转出
    public void rool_out(String address,String amount,String currency_id,String t_password,String service_money,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_rool_out);
        try{
            userClient.AddParam("address",address);//钱包地址
            userClient.AddParam("amount",amount);//转出数量
            userClient.AddParam("currency_id",currency_id);//币种id
            userClient.AddParam("t_password",t_password);//交易密码
            userClient.AddParam("service_money",service_money);//手续费
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //修改密码发送验证码
    public void amend(String account,String send_ip,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_amend);
        try{
            userClient.AddParam("account",account);
            userClient.AddParam("send_class","user_modify");
            userClient.AddParam("send_ip",send_ip);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //修改密码发送验证码
    public void change_password(String account,String verify_code,String send_type,String new_password,String con_password,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_change_password);
        try{
            userClient.AddParam("account",account);
            userClient.AddParam("send_class","user_modify");
            userClient.AddParam("send_type",send_type);
            userClient.AddParam("verify_code",verify_code);
            userClient.AddParam("new_password",new_password);
            userClient.AddParam("con_password",con_password);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //设置新密码
    public void set_password(String new_password,String con_password,String p_key,String send_type,String send_class,String verify_code,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_set_password);
        try{
            userClient.AddParam("new_password",new_password);
            userClient.AddParam("con_password",con_password);
            userClient.AddParam("p_key",p_key);
            userClient.AddParam("send_type",send_type);
            userClient.AddParam("send_class",send_class);
            userClient.AddParam("verify_code",verify_code);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //智能狗列表
    public void smart_dog_list(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_smart_dog_list);
        try{

            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //推广邀请
    public void invite(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_invite);
        try{

            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //关于我们
    public void about(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_about);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }

    //排序列表币种
    public void sort_list(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_sort_list);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
  //币种列表
    public void curr(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_curr);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //语言切换
    public void language(String type,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_language);
        try{
            userClient.AddParam("type",type);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //借款周期
    public void cycle(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_cycle);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
  //更新钱包地址
    public void update(String currency_id,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_update);
        try{
            userClient.AddParam("currency_id",currency_id);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //上传头像
    public void uploding(String u_header,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_uploding);
        try{
            userClient.upLoadData("u_header", u_header, callback, loadingDialog, context);
        }catch (Exception e){

        }
    }
    //推广邀请二维码
    public void extension(MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_extension);
        try{
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //设置收款金额
    public void qrcode(String amount,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_qrcode);
        try{
            userClient.AddParam("amount",amount);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }
    //币种介绍
    public void introduction(String currency_name,MApiResultCallback callback){
        UserClient userClient = new UserClient(UriUtil.ip_introduction);
        try{
            userClient.AddParam("currency_name",currency_name);
            userClient.executePost(callback,loadingDialog,context);
        }catch (Exception e){

        }
    }







}
