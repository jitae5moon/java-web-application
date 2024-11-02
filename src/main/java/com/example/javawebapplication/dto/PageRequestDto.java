package com.example.javawebapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type;

    private String keyword;

    private String link;

    public String[] getTypes() {
        if (!StringUtils.hasText(this.type)) return null;

        return type.split(" ");
    }

    public Pageable getPageable(String... props) {
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    public String getLink() {
        if (link == null) {
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page + "&size=" + this.size);

            if (StringUtils.hasText(this.type)) builder.append("&type=" + this.type);

            if (keyword != null) {
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) { }
            }
            link = builder.toString();
        }

        return link;
    }

}
