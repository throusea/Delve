using TMPro;
using UnityEngine;

/// <summary>
/// 全局单线程计时器
/// </summary>
[RequireComponent(typeof(GameManager))]
public class TimeController: MonoBehaviour
{
    double restOfTime;

    int restOfTimeInt;

    // public readonly double maxTime = 10;

    private GameManager manager;

    public TextMeshProUGUI timeText;

    void Awake()
    {
        manager = GetComponent<GameManager>();
    }

    public double GetRestOfTime()
    {
        return restOfTime;
    }

    public void ResetTime(int maxTime)
    {
        restOfTime = maxTime;
        restOfTimeInt = maxTime;
        UpdateText();
    }

    void Update()
    {
        if (restOfTime < 0.0f)
        {
            manager.OnTurnOver();
        }
        restOfTime -= Time.deltaTime;
        if (restOfTimeInt - 1 > restOfTime)
        {
            restOfTimeInt--;
            UpdateText();
        }
    }

    void UpdateText()
    {
        timeText.text = "剩余时间：" + restOfTimeInt;
    }
}