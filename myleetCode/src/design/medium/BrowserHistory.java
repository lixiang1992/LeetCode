package design.medium;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 设计浏览器的历史记录
 * 你有一个只支持单个标签页的 浏览器 ，最开始你浏览的网页是 homepage ，你可以访问其他的网站 url ，也可以在浏览历史中后退 steps 步或前进 steps 步。
 *
 * 请你实现 BrowserHistory 类：
 *
 * BrowserHistory(string homepage) ，用 homepage 初始化浏览器类。
 * void visit(string url) 从当前页跳转访问 url 对应的页面  。执行此操作会把浏览历史前进的记录全部删除。
 * string back(int steps) 在浏览历史中后退 steps 步。如果你只能在浏览历史中后退至多 x 步且 steps > x ，那么你只后退 x 步。请返回后退 至多 steps 步以后的 url 。
 * string forward(int steps) 在浏览历史中前进 steps 步。如果你只能在浏览历史中前进至多 x 步且 steps > x ，那么你只前进 x 步。请返回前进 至多 steps步以后的 url 。
 *
 */
public class BrowserHistory {

    private String currentPage;
    private Deque<String> backStack = new LinkedList<>();
    private Deque<String> forwardStack = new LinkedList<>();

    public BrowserHistory(String homepage) {
        currentPage = homepage;
    }

    public void visit(String url) {
        backStack.push(currentPage);// 之前的页面进入后退栈
        currentPage = url;// 当前页面更新
        forwardStack.clear();
    }

    public String back(int steps) {
        return getUrlPage(steps,backStack,forwardStack);
    }

    public String forward(int steps) {
        return getUrlPage(steps,forwardStack,backStack);
    }

    /**
     * 获取页面
     * @param steps 步长
     * @param target 目标栈
     * @param receive 接收栈
     * @return 访问的url页面
     */
    private String getUrlPage(int steps,Deque<String> target,Deque<String> receive){
        if (target.isEmpty()){
            // 目标栈为空，返回当前页面
            return currentPage;
        }
        // 如果大于目标栈的最大值，取目标栈
        if (steps > target.size()){
            steps = target.size();
        }
        while (steps > 0){
            receive.push(currentPage);// 接收栈接收当前页面
            currentPage = target.pop();// 目标栈弹出元素为当前页面
            steps--;// 步长-1
        }
        return currentPage;
    }
}
