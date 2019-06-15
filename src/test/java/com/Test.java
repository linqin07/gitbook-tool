package com;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * @Description:
 * @author: LinQin
 * @date: 2019/06/14
 */
public class Test {
    public static void main(String[] args) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable selection = new StringSelection("wqwqw");

        clipboard.setContents(selection, null);

        // 获取剪贴板中的内容
        Transferable contents = clipboard.getContents(null);
        if (contents != null) {
            // 判断剪贴板中的内容是否支持文本
            if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    String text = (String) contents.getTransferData(DataFlavor.stringFlavor);
                    System.out.println(text);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
