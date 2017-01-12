package com.weiteng.weitengapp.app;

import java.io.File;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

import android.content.Context;

public class GlideConfig implements GlideModule {
	@Override
	public void applyOptions(final Context context, final GlideBuilder builder) {
		builder.setDiskCache(new DiskCache.Factory() {
			@Override
			public DiskCache build() {
				File cacheDir = new File(Configuration.DEFAULT_CACHE_PATH);
				cacheDir.mkdirs();

				MemorySizeCalculator calculator = new MemorySizeCalculator(context);
				builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
				builder.setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()));
				builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

				return DiskLruCacheWrapper.get(cacheDir, 256 * 1024 * 1024);
			}
		});
	}

	@Override
	public void registerComponents(Context context, Glide glide) {
		// TODO Auto-generated method stub

	}
}
