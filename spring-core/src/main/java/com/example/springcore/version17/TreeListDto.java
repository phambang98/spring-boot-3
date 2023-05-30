package com.example.springcore.version17;

import java.util.List;

public class TreeListDto {
        private String id;

        private String parentId;
        private String name;
        private List<TreeListDto> subTreeList;

        public TreeListDto() {
        }

        static {
            System.out.println("ez");
        }

        public TreeListDto(String id, String parentId, String name) {
            this.id = id;
            this.parentId = parentId;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<TreeListDto> getSubTreeList() {
            return subTreeList;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSubTreeList(List<TreeListDto> subTreeList) {
            this.subTreeList = subTreeList;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

    }