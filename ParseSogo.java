package org.heng.viewpoint.ciku;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 解析sogo词库工具类
 * 到这里去挑选下载词库文件（.scel）
 * **/
public class ParseSogo {
  
  public static void main(String[] args)throws Exception {
   	 sogou("D:\\歌手人名大全【官方推荐】.scel","D:\\goods1.txt",false);
  }
   
  /**
   * 读取scel的词库文件
   * 生成txt格式的文件
   * @param inputPath 输入路径
   * @param outputPath 输出路径
   * @param isAppend  是否拼接追加词库内容 
   * true 代表追加,false代表重建
   * 
   * **/
   private static void sogou(String inputPath,String outputPath,boolean isAppend) throws IOException{  
     File file=new File(inputPath);  
     if(!isAppend){
     if(Files.exists(Paths.get(outputPath),LinkOption.values())){
       System.out.println("存储此文件已经删除");
       Files.deleteIfExists(Paths.get(outputPath));
       
     }
     }
     RandomAccessFile raf=new RandomAccessFile(outputPath, "rw");
    
     int count=0;
     SougouScelMdel model = new SougouScelReader().read(file);  
     Map<String,List<String>> words = model.getWordMap(); //词<拼音,词>  
     Set<Entry<String,List<String>>> set = words.entrySet();  
     Iterator<Entry<String,List<String>>> iter = set.iterator();  
     while(iter.hasNext()){  
       Entry<String,List<String>> entry = iter.next();  
       List<String> list = entry.getValue();  
       int size = list.size();  
       for(int i = 0; i < size; i++){  
         String word = list.get(i);  
         
         //System.out.println(word); 
         raf.seek(raf.getFilePointer());
         raf.write((word+"\n").getBytes());//写入txt文件
         count++;
         
         
       }  
     }  
     raf.close();
     System.out.println("生成txt成功！,总计写入: "+count+" 条数据！");
   }  

}
