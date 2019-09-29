package bbangtoken.tckkj.com.bbangtoken.Bean;

/**
 * 后台接口返回的数据格式
 * Created by ZhangKe on 2018/6/27.
 */
public class CommonResponseEntity {

  public String ch;
  public long ts;
  public tick_item tick;
  public exchange_rate_item exchange_rate;

  public static class tick_item {
      public long id;
      public float low;
      public float high;
      public float open;
      public float close;
      public float vol;
      public float amount;
      public float version;
      public float count;
    }
    public static class exchange_rate_item{
      public long data_time;
      public String name;
      public float rate;
      public long time;
    }
}
