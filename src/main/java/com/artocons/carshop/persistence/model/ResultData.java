package com.artocons.carshop.persistence.model;

import java.math.BigDecimal;

public class ResultData {

        Integer count ;
        BigDecimal total;

        public ResultData(){

        }
        public ResultData(Integer count, BigDecimal total) {
                this.count = count;
                this.total = total;
        }
//        public Integer getCount(){
//                return count;
//        }
        public void setCount(Integer count){
                this.count = count;
        }
//        public BigDecimal getTotal(){
//                return total;
//        }
        public void setTotal(BigDecimal total){
                this.total = total;
        }
}
