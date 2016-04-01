package com.vlsi.imageplayer.view;

import java.io.File;
import java.io.FilenameFilter;

public class MyFilter implements FilenameFilter{
 private String[] extension;   
 public MyFilter(){
  extension = new String[]{".jpg", ".JPG", ".gif", ".GIF", ".png", ".PNG", ".jpeg", ".JPEG"}; 
 }
 public MyFilter(String[] extension){
  this.extension = extension; 
 }
 public boolean accept(File dir,String name){
  for(String s : extension){
   if(name.endsWith(s)){
    return true; 
   }
  } 
  return  false; 
 } 
}