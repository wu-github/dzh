package com.wurd.bd.train.hadoop.mrjoin;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
public class CombineValues implements WritableComparable<CombineValues>{
    //private static final Logger logger = LoggerFactory.getLogger(CombineValues.class);
    private Text joinKey;//链接关键字
    private Text flag;//文件来源标志
    private Text secondPart;//除了链接键外的其他部分
    public void setJoinKey(Text joinKey) {
        this.joinKey = joinKey;
    }
    public void setFlag(Text flag) {
        this.flag = flag;
    }
    public void setSecondPart(Text secondPart) {
        this.secondPart = secondPart;
    }
    public Text getFlag() {
        return flag;
    }
    public Text getSecondPart() {
        return secondPart;
    }
    public Text getJoinKey() {
        return joinKey;
    }
    public CombineValues() {
        this.joinKey =  new Text();
        this.flag = new Text();
        this.secondPart = new Text();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        this.joinKey.write(out);
        this.flag.write(out);
        this.secondPart.write(out);
    }
    @Override
    public void readFields(DataInput in) throws IOException {
        this.joinKey.readFields(in);
        this.flag.readFields(in);
        this.secondPart.readFields(in);
    }
    @Override
    public int compareTo(CombineValues o) {
        return this.joinKey.compareTo(o.getJoinKey());
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "[flag="+this.flag.toString()+",joinKey="+this.joinKey.toString()+",secondPart="+this.secondPart.toString()+"]";
    }
}