package struct.datastruct.stack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 栈的练习
 */
public class LinkStack {

    /**
     * 42.接雨水
     * @param height
     * @return
     */
    public int trap(int[] height) {
        if (height.length == 0){
            return 0;
        }
        int max = 0;// 最大雨水量
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < height.length; i++) {
            // 栈不为空，且当前元素比栈顶元素大
            while (!stack.isEmpty() && height[i] > height[stack.peek()]){
                int curIndex = stack.pop();
                // 高度和栈顶一样的全部弹出
                while (!stack.isEmpty() && height[i] == height[curIndex]){
                    stack.pop();
                }
                if (stack.isEmpty()){
                    break;
                }
                // top此时指向的是此次接住的雨水的左边界的位置。右边界是当前的柱体，即i。
                // Math.min(height[top], height[i]) 是左右柱子高度的min，减去height[curIdx]就是雨水的高度。
                // i - top - 1 是雨水的宽度。
                int top = stack.peek();
                max += (Math.min(height[i],height[top])-height[curIndex]) * (i - top - 1);
            }
            stack.push(i);
        }
        return max;
    }

    /**
     * 84. 柱状图中最大的矩形
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        if (heights.length == 0){
            return 0;
        }
        int max = 0;
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < heights.length; i++) {
            // 当前元素比栈顶元素小，栈顶出栈
            while (!stack.isEmpty() && heights[i] < heights[stack.peek()]){
                int topIndex = stack.pop();
                // 高度相同的出栈
                while (!stack.isEmpty() && heights[topIndex] == heights[stack.peek()]){
                    stack.pop();
                }
                int curWidth;
                if (stack.isEmpty()) {
                    curWidth = i;
                } else {
                    curWidth = i - stack.peek() - 1;
                }
                max = Math.max(max, heights[topIndex] * curWidth);

            }
            stack.push(i);
        }
        // 栈不空
        while (!stack.isEmpty()){
            int curHeight = heights[stack.pop()];
            while (!stack.isEmpty() && heights[stack.peek()] == curHeight) {
                stack.pop();
            }
            int curWidth;
            if (stack.isEmpty()) {
                curWidth = heights.length;
            } else {
                curWidth = heights.length - stack.peek() - 1;
            }
            max = Math.max(max, curHeight * curWidth);

        }
        return max;
    }

    /**
     * 739.每日温度
     * 根据每日 气温 列表，请重新生成一个列表，对应位置的输入是你需要再等待多久温度才会升高超过该日的天数。
     * 如果之后都不会升高，请在该位置用 0 来代替。
     *
     * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
     *
     * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
     *
     * @param T 温度数组
     * @return res
     */
    public int[] dailyTemperatures(int[] T) {
        int[] res = new int[T.length];
        // 栈只记录下标
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < T.length; i++) {
            // 当前温度比栈顶元素温度高，低的元素出栈并记录到数组中
            while (!stack.isEmpty() && T[i] > T[stack.peek()]){
                // 天数之差
                int last = stack.pop();
                res[last] = i - last;
            }
            // 当前温度下标入栈
            stack.push(i);
        }
        // T遍历结束，栈里还有元素，表示他们无法升温了，天数设置为0
        while (!stack.isEmpty()){
            res[stack.pop()] = 0;
        }
        return res;
    }
}
