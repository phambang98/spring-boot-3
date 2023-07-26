package com.example.core.version17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2 {

    public static void main(String[] args) {

        // input
        ArrayList<Pair> pairs = new ArrayList<Pair>();
        pairs.add(new Pair( "H" , "G"));
        pairs.add(new Pair( "F" , "G"));
        pairs.add(new Pair( "G" , "D"));
        // ...


        // Arrange
        // String corresponds to the Id
        Map<String, MegaMenuDTO> hm = new HashMap<>();


        // you are using MegaMenuDTO as Linked list with next and before link 

        // populate a Map
        for(Pair p:pairs){

            //  ----- Child -----
            MegaMenuDTO mmdChild ;
            if(hm.containsKey(p.getChildId())){
                mmdChild = hm.get(p.getChildId());
            }
            else{
                mmdChild = new MegaMenuDTO();
                hm.put(p.getChildId(),mmdChild);
            }           
            mmdChild.setId(p.getChildId());
            mmdChild.setParentId(p.getParentId());
            // no need to set ChildrenItems list because the constructor created a new empty list



            // ------ Parent ----
            MegaMenuDTO mmdParent ;
            if(hm.containsKey(p.getParentId())){
                mmdParent = hm.get(p.getParentId());
            }
            else{
                mmdParent = new MegaMenuDTO();
                hm.put(p.getParentId(),mmdParent);
            }
            mmdParent.setId(p.getParentId());
            mmdParent.setParentId("null");                              
            mmdParent.addChildrenItem(mmdChild);


        }

        // Get the root
        List<MegaMenuDTO> DX = new ArrayList<>();
        for(MegaMenuDTO mmd : hm.values()){
            if(mmd.getParentId().equals("null"))
                DX.add(mmd);
        }

        // Print 
        for(MegaMenuDTO mmd: DX){
            System.out.println("DX contains "+DX.size()+" that are : "+ mmd);
        }

    }

}