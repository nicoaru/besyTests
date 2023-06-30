package com.asj.besyTest.model.entities;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChuckNorrisJoke {

    public String id;
    public String icon_url;
    public String url;
    public String value;
    public String created_at;
    public List<?> categories;
    public String updated_at;

}
