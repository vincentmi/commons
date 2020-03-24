package com.vnzmi.commons.rest;
import com.github.pagehelper.PageInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormattedPager<T>  extends  PageInfo<T>{
    private PageItemProcessor processor  = null;

    public FormattedPager(List<T> object)
    {
        super(object);
    }

    public FormattedPager<T> create(List<T> object)
    {
        return new FormattedPager<T>(object);
    }

    public FormattedPager<T> setProcessor(PageItemProcessor processor)
    {
        this.processor = processor;
        return this;
    }

    public Map<String,Object> getPage()
    {
        Map<String,Object> pagination = new HashMap();
        pagination.put("total_page", this.getPages());
        pagination.put("total_record", this.getTotal());
        pagination.put("page", this.getPageNum());
        pagination.put("page_size", this.getPageSize());
        pagination.put("more", this.isHasNextPage());

        Map<String,Object> res = new HashMap();
        if(this.processor == null)
        {
            res.put("items", this.getList());
        }else{
            List list = this.getList();
            List<Object> resultList = new ArrayList<Object>();
            for(int i = 0,max = list.size();i<max;i++)
            {
                resultList.add(this.processor.process(list.get(i)));
            }
            res.put("items", resultList);
        }

        res.put("pagination", pagination);
        return res;
    }
}
