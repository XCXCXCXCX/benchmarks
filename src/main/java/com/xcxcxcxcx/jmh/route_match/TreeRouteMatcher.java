package com.xcxcxcxcx.jmh.route_match;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XCXCXCXCX
 * @date 2020/8/19 2:31 下午
 */
public class TreeRouteMatcher implements RouteMatcher {

    private static final String GENERAL_PATTERN = "**";

    private TreeNode tree = new TreeNode("");

    @Override
    public void setRoutes(List<Route> routes) {
        for (Route route : routes) {
            String path = route.getPath();
            String[] nodePaths = path.split("/");
            addNode(nodePaths);
        }
    }

    private void addNode(String[] nodePaths) {
        TreeNode root = tree;
        int curLevel = 0;
        do {
            List<TreeNode> treeNodes = root.sons;
            TreeNode temp = null;
            for (TreeNode treeNode : treeNodes) {
                if (treeNode.nodePath.equals(nodePaths[curLevel])) {
                    temp = treeNode;
                    break;
                }
            }
            if (temp == null) {
                temp = new TreeNode(nodePaths[curLevel]);
                treeNodes.add(temp);
            }
            root = temp;
        }while (++curLevel < nodePaths.length);
    }

    private TreeNode findNode(String[] nodePaths) {
        TreeNode root = tree;
        int curLevel = 0;
        do {
            List<TreeNode> treeNodes = root.sons;
            TreeNode temp = null;
            for (TreeNode treeNode : treeNodes) {
                if ("**".equals(treeNode.nodePath)) {
                    return treeNode;
                }
                if (treeNode.nodePath.equals(nodePaths[curLevel])) {
                    temp = treeNode;
                    break;
                }
            }
            if (temp == null) {
                return null;
            }
            root = temp;
        }while (++curLevel < nodePaths.length);
        return root;
    }

    @Override
    public Route match(String path) {
        String[] nodePaths = path.split("/");
        TreeNode node = findNode(nodePaths);
        return node == null ? null : new Route(node.nodePath);
    }

    private static class TreeNode {
        private String nodePath;

        TreeNode(String nodePath) {
            this.nodePath = nodePath;
        }

        private List<TreeNode> sons = new ArrayList<>();

    }

}
