package com.vnzmi.commons.rest;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PageResponse {
    private PageMeta pagination = new PageMeta();
    private List<Object> items;

    @JsonIgnore
    private PageItemProcessor processor = null;

    public PageResponse(Page page) {
        init(page);
    }

    public PageResponse(Page page, PageItemProcessor processor) {
        this.processor = processor;
        init(page);
    }


    public PageMeta getPagination() {
        return pagination;
    }

    public void setPagination(PageMeta pagination) {
        this.pagination = pagination;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public PageItemProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(PageItemProcessor processor) {
        this.processor = processor;
    }

   /* private void initPageInfo(PageInfo page)
    {
        if(page.getTotal() == 0)
        {
            setItems(Collections.EMPTY_LIST);
        }else{
            PageMeta pm = new PageMeta();
            pm.setCurrentPage(page.getPageNum());
            pm.setPageSize(page.getSize());
            pm.setTotalPage(page.getPages());
            pm.setTotal(page.getTotal());
            this.setPagination(pm);
            if(processor != null)
            {
                List data = page.getList();
                Object object ;
                List<Object> list = new ArrayList<>();
                data.forEach(e -> {
                    list.add(processor.process(e));
                });
                setItems(list);
            }else{
                this.setItems(page.getList());
            }
        }
    }*/

    private void init(Page page) {
        if (page.isEmpty()) {
            setItems(Collections.EMPTY_LIST);
        } else {
            PageMeta pm = new PageMeta();
            pm.setCurrentPage(page.getNumber());
            pm.setPageSize(page.getSize());
            pm.setTotalPage(page.getTotalPages());
            pm.setTotal(page.getTotalElements());
            pm.setMore(page.getNumber() < page.getTotalPages());
            this.setPagination(pm);

            if (processor != null) {
                Iterator data = page.getContent().iterator();
                Object object;
                List<Object> list = new ArrayList<>();
                while (data.hasNext()) {
                    object = data.next();
                    list.add(processor.process(object));
                }
                setItems(list);
            } else {
                this.setItems((List<Object>) page.getContent());
            }
        }
    }

    public static PageResponse create(Page<?> page) {
        return new PageResponse(page);
    }


    public static PageResponse create(Page<?> page, PageItemProcessor processor) {
        return new PageResponse(page, processor);
    }

}