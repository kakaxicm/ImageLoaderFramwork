package com.qicode.kakaxicm.imageloaderframwork;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.qicode.kakaxicm.imageloaderframwork.adapter.UniversalAdapter;
import com.qicode.kakaxicm.imageloaderframwork.adapter.UniversalViewHolder;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.ImageLoader;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.cache.DiskCache;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.cache.DoubleCache;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.config.ImageLoaderConfig;
import com.qicode.kakaxicm.imageloaderframwork.imageloader.policy.ReversePolicy;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageLoader imageLoader;
    private final int REQUESTCODE = 101;

    RecyclerView mRcv;
    List<String> mUrls = new ArrayList<>();
    String[] mUrlArr = {
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/000bb9836ea21a9e5989d59387477e83.JPG",
            "http://art-sign-pro.oss-cn-beijing.aliyuncs.com/image/expert_sign/003b5b828e24f3e01dd832c725fc6da0.JPG"

    };
    UniversalAdapter<String> mAdapter = new UniversalAdapter<>(mUrls, new UniversalAdapter.OnBindDataInterface<String>() {
        @Override
        public void onBindData(String model, UniversalViewHolder holder, int pos, int type) {
            ImageView iv = holder.getSubView(R.id.img);
            imageLoader.loadImage(iv, model);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_img;
        }
    });

    private String[] ps = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission();
        } else {
            testImageLoader();
        }

        for (int i = 0; i < mUrlArr.length; i++) {
            mUrls.add(mUrlArr[i]);
        }
        mRcv = findViewById(R.id.rcv);
        mRcv.setLayoutManager(new GridLayoutManager(this, 1));
        mRcv.setAdapter(mAdapter);

    }

    private void permission() {
        List<String> permissionLists = new ArrayList<>();
        //检查权限
        for (String per : ps) {
            if (ContextCompat.checkSelfPermission(this, per) != PackageManager.PERMISSION_GRANTED) {
                permissionLists.add(per);
            }
        }

        if (!permissionLists.isEmpty()) {//说明肯定有拒绝的权限
            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), REQUESTCODE);
        } else {
            Toast.makeText(this, "权限都授权了，可以搞事情了", Toast.LENGTH_SHORT).show();
            testImageLoader();
        }
    }

    /**
     * ImageLoader测试
     */
    private void testImageLoader() {
        //配置
        ImageLoaderConfig.Builder build = new ImageLoaderConfig.Builder();
        build.setThreadCount(3) //线程数量
                .setLoadPolicy(new ReversePolicy()) //加载策略
//                .setCachePolicy(DiskCache.getInstance(this)) //缓存策略
                .setCachePolicy(new DoubleCache(this))
                .setLoadingImage(R.drawable.loading)
                .setFaildImage(R.drawable.not_found);

        ImageLoaderConfig config = build.build();
        //初始化
        imageLoader = ImageLoader.getInstance(config);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "某一个权限被拒绝了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //其他逻辑(这里当权限都同意的话就执行打电话逻辑
                testImageLoader();
            }
        }
    }
}
