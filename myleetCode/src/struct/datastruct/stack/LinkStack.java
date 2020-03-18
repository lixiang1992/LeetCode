package struct.datastruct.stack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 栈的练习
 */
public class LinkStack {

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
