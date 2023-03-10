package com.example.version17;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeList {

    public static void main(String[] args) {
        List<TreeListDto> dtoList = new ArrayList<TreeListDto>() {{
            add(new TreeListDto("7", "3", "cap 7"));
            add(new TreeListDto("1", "0", "cap 1"));
            add(new TreeListDto("2.1", "1", "cap 2.1"));
            add(new TreeListDto("2.2", "1", "cap 2.2"));
            add(new TreeListDto("66", "55", "cap 66"));
            add(new TreeListDto("3", "2.1", "cap 3.2.1"));
            add(new TreeListDto("0", "", "cap 0"));
            add(new TreeListDto("99", "88", "cap 99"));
        }};
        dtoList = buildHierarchicalList(dtoList);

        System.out.println(dtoList);

    }

    private static List<TreeListDto> buildHierarchicalList(final List<TreeListDto> originalList) {
        final List<TreeListDto> listResult = new ArrayList<>(originalList);
        //find highest rank
        originalList.forEach(element -> {
            originalList.forEach(element2 -> {
                if (element2.getId().equals(element.getParentId()) && !element2.getId().equals(element.getId())) {
                    listResult.remove(element);
                }
            });
        });
        if (!CollectionUtils.isEmpty(listResult)) {
            Map<String, List<TreeListDto>> groupsParentCode = originalList.stream().
                    collect(Collectors.groupingBy(x -> x.getParentId() == null ? "" : x.getParentId(), Collectors.toList()));
            for (int i = 0; i < listResult.size(); i++) {
                addSubCategory(listResult.get(i), groupsParentCode);
            }
        }
        return listResult;
    }

    private static void addSubCategory(TreeListDto treeListDto, Map<String, List<TreeListDto>> map) {
        if (map.containsKey(treeListDto.getId())) {
            treeListDto.setSubTreeList(map.get(treeListDto.getId()));
            for (int i = 0; i < treeListDto.getSubTreeList().size(); i++)
                addSubCategory(treeListDto.getSubTreeList().get(i), map);
        } else {
            treeListDto.setSubTreeList(new ArrayList<>());
        }
    }


}
