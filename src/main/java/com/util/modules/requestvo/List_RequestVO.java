package com.util.modules.requestvo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class List_RequestVO<T> {
    List<T> list;
}
