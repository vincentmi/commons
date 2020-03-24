package com.vnzmi.commons.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PageMeta {
        @JsonProperty(value="total_record")
        private long total = 0;

        @JsonProperty(value="total_page")
        private long  totalPage = 0 ;


        @JsonProperty("page")
        private int currentPage=0;

        @JsonProperty(value = "page_size")
        private int pageSize = 20;

}
