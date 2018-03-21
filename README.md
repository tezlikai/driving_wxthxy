1、日志管理工具：logger

    Options
    Logger.d("debug");
    Logger.e("error");
    Logger.w("warning");
    Logger.v("verbose");
    Logger.i("information");
    Logger.wtf("wtf!!!!");

    String format arguments are supported
    Logger.d("hello %s", "world");

    Collections support (only available for debug logs)
    Logger.d(MAP);
    Logger.d(SET);
    Logger.d(LIST);
    Logger.d(ARRAY);

    Json and Xml support (output will be in debug level)
    Logger.json(JSON_CONTENT);
    Logger.xml(XML_CONTENT);

    设置log

    FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
      .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
      .methodCount(0)         // (Optional) How many method line to show. Default 2
      .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
      .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
      .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
      .build();

    Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

    Save logs to the file
    Logger.addLogAdapter(new DiskLogAdapter());

    Add custom tag to Csv format strategy

    FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
      .tag("custom")
      .build();
    Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));

2、图片加载框架 Glide  https://github.com/bumptech/glide

    Simple:

    // For a simple view:
    @Override public void onCreate(Bundle savedInstanceState) {
      ...
      ImageView imageView = (ImageView) findViewById(R.id.my_image_view);

      GlideApp.with(this).load("http://goo.gl/gEgYUd").into(imageView);
    }

    // For a simple image list:
    @Override public View getView(int position, View recycled, ViewGroup container) {
      final ImageView myImageView;
      if (recycled == null) {
        myImageView = (ImageView) inflater.inflate(R.layout.my_image_view, container, false);
      } else {
        myImageView = (ImageView) recycled;
      }

      String url = myUrls.get(position);

      GlideApp
        .with(myFragment)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.loading_spinner)
        .into(myImageView);

      return myImageView;
    }

3、权限控制 easypermissions

      所在的Activity/Fragment 实现 EasyPermissions.PermissionCallbacks 接口

      public class ClinicalFragment extends BaseFragment<ClinicalContract.View, ClinicalPresenter>
      implements ClinicalContract.View ,
      EasyPermissions.PermissionCallbacks{ ... }

      实现以下方法：

          @Override
          public void onPermissionsGranted(int requestCode, List<String> perms) {
              // Some permissions have been granted
              if (requestCode == RC_LOCATION_CONTACTS_PERM) {
                  toPdfViewPage();
              }

          }

          @Override
          public void onPermissionsDenied(int requestCode, List<String> perms) {
              // Some permissions have been denied
              if (requestCode == RC_LOCATION_CONTACTS_PERM) {
                  // Do something after user returned from app settings screen, like showing a Toast.
                  if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                      new AppSettingsDialog.Builder(this)
                              .setTitle("提示")
                              .setPositiveButton("设置")
                              .setNegativeButton("取消")
                              .setRequestCode(RC_LOCATION_CONTACTS_PERM)
                              .build()
                              .show();
                  }
              }

          }

          @Override
          public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
              super.onRequestPermissionsResult(requestCode, permissions, grantResults);
              // Forward results to EasyPermissions
              EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
          }

      操作之前，需要判断应用是否有对应权限

      String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
      if (EasyPermissions.hasPermissions(getActivity(), perms)) {
             toPdfViewPage();
           } else {
              EasyPermissions.requestPermissions(getActivity(), "资料文件需要读取本地权限", RC_LOCATION_CONTACTS_PERM, perms);
           }

4、数据库框架 DBFlow  https://github.com/Raizlabs/DBFlow

   具体使用参考：度娘