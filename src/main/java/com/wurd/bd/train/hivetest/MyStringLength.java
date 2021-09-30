package com.wurd.bd.train.hivetest;


import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector.Category;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * 自定义UDF函数，需要继承GenericUDF类
 * 需求：计算指定字符串的长度
 */
public class MyStringLength extends GenericUDF {

    /**
     * ObjectInspector接口注释：
     * ObjectInspector实例代表了一个类型的数据在内存中存储的特定类型和方法。
     * 对于本机Java对象，我们可以通过成员变量和方法直接访问其内部结构。
     * ObjectInspector是一种将该功能委托给对象之外的方法，这样我们可以更好地控制这些操作的行为。
     * 5种类型：基本类型（Primitive），集合（List），键值对映射（Map），结构体（Struct），联合体（Union）
     * @param args 输入参数类型的鉴别器对象
     * @return 返回值类型的鉴别器对象
     * @throws UDFArgumentException
     */
    @Override
    public ObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
        // 判断输入参数的个数
        if(args.length !=1) {
            throw new UDFArgumentTypeException(0, "Input Args Type Error!");
        }

        // 判断输入参数的类型
        if(!args[0].getCategory().equals(Category.PRIMITIVE)) {
            throw new UDFArgumentTypeException(0, "Input Args Type is not basic type!");
        }
        // 函数本身返回值为int， 需要返回int类型的鉴别器对象
        // 基本类型工厂方法获得Java int类型的鉴别器对象
        return PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    }

    /**
     * 函数的逻辑处理
     * @param args 输入的参数
     * @return 返回值
     * @throws HiveException
     */
    @Override
    public Object evaluate(DeferredObject[] args) throws HiveException {
        if(args[0].get() == null) {
            return 0;
        }
        return args[0].get().toString().length();
    }

    @Override
    public String getDisplayString(String[] strings) {
        return "";
    }
}
