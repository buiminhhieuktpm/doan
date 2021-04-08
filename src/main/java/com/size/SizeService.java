package com.size;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class SizeService {
 @Autowired
 SizeRepository srepo;
 public List<Size> listAll(){
	 return srepo.findAll();
 }
 public List<Size> listByProductId(int id){
	 return srepo.findSizeByProductId(id);
 }
 public Optional<Size> listByName(int pid,String name){
	 return srepo.findSizeByName(pid, name);
 }
 public List<Size> listByPoductAndAmount(int pid){
	 return srepo.findSizeByProductAndAmount(pid);
 }
 public void add(Size s) {
	 srepo.save(s);
 }
 public void delete(int id) {
	 srepo.deleteById(id);
 }
 public Size get(int id) {
	 return srepo.findById(id).get();
 }
}
