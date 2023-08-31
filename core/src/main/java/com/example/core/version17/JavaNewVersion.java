package com.example.core.version17;


import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JavaNewVersion {
    public void merging(int arr[], int temp[], int low, int mid, int high) {
        int l1, l2, i;

        l1 = low;
        l2 = mid + 1;
        for (i = low; l1 <= mid && l2 <= high; i++) {
            if (arr[l1] <= arr[l2]) {
                temp[i] = arr[l1++];
            } else {
                temp[i] = arr[l2++];
            }
        }

        while (l1 <= mid) {
            temp[i++] = arr[l1++];
        }
        while (l2 <= high) {
            temp[i++] = arr[l2++];
        }
        for (i = low; i <= high; i++) {
            arr[i] = temp[i];
        }
    }

    public void sort(int arr[], int temp[], int low, int high) {
        int mid;

        if (low < high) {
            mid = (low + high) / 2;
            sort(arr, temp, low, mid);
            sort(arr, temp, mid + 1, high);
            merging(arr, temp, low, mid, high);
            // hien thi mang
            display(arr);
        } else {
            return;
        }
    }

    public void display(int arr[]) {
        int i;
        System.out.print("[");

        // Duyet qua tat ca phan tu
        for (i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

        System.out.print("]\n");
    }

    public static void main(String[] args) {
        // khoi tao mang arr
        int arr[] = {8, 2, 0, 7};
        int temp[] = new int[4];

        JavaNewVersion sapXeptron = new JavaNewVersion();
        System.out.println("Mang du lieu dau vao: ");
        sapXeptron.display(arr);
        System.out.println("-----------------------------");
        sapXeptron.sort(arr, temp, 0, arr.length - 1);
        System.out.println("-----------------------------");
        System.out.println("\nMang sau khi da sap xep: ");
        sapXeptron.display(arr);
    }

}
