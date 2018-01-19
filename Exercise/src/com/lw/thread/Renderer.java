package com.lw.thread;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.sun.scenario.effect.ImageData;

/**
 * 下载使用多个线程工作，提高渲染器性能，将串行下载过程变为并行过程，减少下载图片总时间
 * CompletionService
 * ExecutorCompletionService
 * @author lenovo
 *
 */
public abstract class Renderer {
	private final ExecutorService executor;

	public Renderer(ExecutorService executor) {
		this.executor = executor;
	}

	void renderPage(CharSequence source) {
		List<ImageInfo> info = scanForImageInfo(source);
		CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(
				executor);
		for(final ImageInfo imageInfo: info){
			//submit方法将任务提交给Executor，这里就创建了多个线程下载图片
			completionService.submit(new Callable<ImageData>() {
				
				@Override
				public ImageData call() throws Exception {
					return imageInfo.downLoadImage();
				}
			});
		}
		renderText(source);
		try{
			for(int t=0,n=info.size();t<n;t++){
				//take,poll方法用来获得已经完成的结果，并封装为Future
				Future<ImageData> f = completionService.take();
				ImageData imageData = f.get();
				renderImage(imageData);//渲染图片
			}
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
		}catch(ExecutionException e){
		}
	}

	abstract void renderText(CharSequence s);

	/**
	 * 读取图片信息
	 * @param s
	 * @return
	 */
    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);
	

	class ImageInfo {

		public ImageData downLoadImage() {
			return null;
		}

	}
}
