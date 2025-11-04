using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class EnemyController : MonoBehaviour
{
    public int health;

    public List<Skill> skillPool;  // 可用技能池

    public SkillController skillController;

    TextMeshProUGUI nameText;

    Slider healthSlider; // 生命值UI
    void Awake()
    {
        nameText = GetComponentInChildren<TextMeshProUGUI>();
        healthSlider = GetComponentInChildren<Slider>();
        healthSlider.maxValue = 5;
        healthSlider.minValue = 0;
        healthSlider.value = 5;
    }

    /// <summary>
    /// 更新血条
    /// </summary>
    public void UpdateHealthSlider()
    {
        healthSlider.value = health;
    }
}