using System.Collections.Generic;
using UnityEngine;

public class TestCombinationChecker : MonoBehaviour
{
    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        RunTest(new List<int> { 1, 1 }, "对子");

        RunTest(new List<int> { 2, 2, 2 }, "三条");

        RunTest(new List<int> { 2, 2, 2, 2 }, "无效组合");

    }
    
    void RunTest(List<int> dices, string testName)
    {
        var res = CombinationChecker.CheckCombination(dices);

        Debug.Log($"--- 测试: {testName} ({string.Join(", ", dices)}) ---");
        if (res.ToString() == "NULL")
        {
            Debug.Log("无效组合");
        }

        else
        {
            Debug.Log("有效组合");
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
