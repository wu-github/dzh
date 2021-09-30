package com.wurd.bd.train.hivetest;

import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * 5种类型：基本类型（Primitive），集合（List），键值对映射（Map），结构体（Struct），联合体（Union）
 */
public class MyUDTF extends GenericUDTF {

    private ArrayList<String> outList = new ArrayList<>();

    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        // 定义输出数据的列名和类型
        List<String> fieldNames = new ArrayList<>();
        List<ObjectInspector> fieldOIs = new ArrayList<>();
        // 添加输出数据的列名和类型
        fieldNames.add("lineToWord");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }


    @Override
    public void process(Object[] args) throws HiveException {
        // 获取原始数据
        String arg = args[0].toString();
        /// 获取数据传入的第二个参数，此处为分隔符
        String splitKey = args[1].toString();
        // 将原始数据按照传入的分隔符进行切分
        String[] fields = arg.split(splitKey);
        // 遍历切分后的结果，并写出
        for (String field : fields) {
            // 集合为复用的，首先清空集合
            outList.clear();
            // 将每一个单词添加至集合
            outList.add(field);
            // 将集合内容写出
            forward(outList);
        }
    }

    @Override
    public void close() throws HiveException {

    }
}
