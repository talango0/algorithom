class Solution {
public:
    vector<vector<int>> threeSum(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        //求和为0 的三元数组
        return threeSumTarget(nums, 0);
    }
    vector<vector<int>> threeSumTarget(vector<int> & nums, int sum){
        int n = nums.size();
        vector<vector<int>> res;

        //穷举第一个元素
        for (int i = 0; i<n ; i++) {
            vector<vector<int>> tuples = towSumTarget(nums, i+1, sum-nums[i]);

            //如果存在满足条件的二元组，再加上 num[i] 就是三元组
            for (vector<int> & tuple: tuples) {
                tuple.push_back(nums[i]);
                res.push_back(tuple);
            }
            while (i<n-1 && nums[i] == nums[i+1]) {i++;}
        }
        return res;

    }

    // 从 nums[start] 开始，计算有序数组 nums 种所有和为 target 的二元组
    vector<vector<int>> towSumTarget(vector<int> & nums, int start, int target) {
        //左指针从 start 开始
        int lo = start, hi = nums.size()-1;
        vector<vector<int>> res;
        while(lo < hi) {
            int left = nums[lo];
            int right = nums[hi];
            int sum = left + right;
            if (sum < target) {
                while(lo<hi && nums[lo] == left){lo++;}
            }
            else if (sum > target) {
                while(lo<hi && nums[hi] == right){hi--;}
            }
            else {
                res.push_back({left,right});
                while(lo < hi && nums[lo] == left){ lo++; }
                while(lo < hi && nums[hi] == right){ hi--; }
            }
        }
        return res;
    }
};