package com.luwei.recyclerview.adapter.extension;

import com.luwei.recyclerview.adapter.multitype.LwAdapter;

import java.util.List;

/**
 * {@link LwAdapter}的一个扩展接口，扩展原有的数据集，目前有两个实现类
 * {@link HeaderExtension}用于添加Header,{@link LwAdapter#addHeader(Object)}
 * {@link FooterExtension}用于添加Footer,{@link LwAdapter#addFooter(Object)}
 *
 * Created by Mr_Zeng
 *
 * @date 2018/11/13
 */
public interface Extension {

    /**
     * @param position 当前{@link Extension}的相对Item路径
     * @return
     */
    Object getItem(int position);

    /**
     * 根据传入的参数判断是否属于该Extension
     * @param adapterPos Adapter 的真实 position
     * @return true 代表属于该 Extension , false 相反
     */
    boolean isInRange(int adapterSize,int adapterPos);

    /**
     * @return 该Extension的size
     */
    int getItemSize();

    /**
     * 添加Item
     * @param o 需要添加的Item
     */
    void add(Object o);

    /**
     * 向指定索引添加 Item
     * @param index 插入位置索引
     * @param o 需要添加的Item
     */
    void add(int index,Object o);

    /**
     * 移除 Item
     * @param o 要移除的 Item
     */
    void remove(Object o);

    /**
     * 移除对应索引的 Item
     * @param index 要移除的 Item 的索引
     */
    void remove(int index);

    void addAll(List<Object> objects);

    void clear();
}
