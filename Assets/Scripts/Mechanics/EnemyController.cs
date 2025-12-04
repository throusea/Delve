using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.InputSystem;
using UnityEngine.UI;

public class EnemyController : MonoBehaviour
{
    public EnemyManager manager;
    public int health;

    public List<Skill> skillPool;  // 可用技能池

    public SkillController skillController;

    TextMeshProUGUI nameText;

    Slider healthSlider; // 生命值UI

    public DelveControls inputCtrl;
    void Awake()
    {
        inputCtrl = new DelveControls();
        nameText = GetComponentInChildren<TextMeshProUGUI>();
        healthSlider = GetComponentInChildren<Slider>();
        healthSlider.maxValue = 5;
        healthSlider.minValue = 0;
        healthSlider.value = 5;
        UpdateHealthSlider();
    }

    void OnEnable()
    {
        inputCtrl.GamePlay.PrimaryTouch.performed += OnEnemyClick;
        inputCtrl.Enable();
    }

    void OnDisable()
    {
        inputCtrl.GamePlay.PrimaryTouch.performed -= OnEnemyClick;
        inputCtrl.Disable();
    }

    public void OnEnemyClick(InputAction.CallbackContext context)
    {
        if (InputToWorldConverter.RectContainsScreenMousePosition(GetComponent<RectTransform>()))
        {
            Debug.Log("Enemy Clicked: " + gameObject.name);
            manager.OnEnemyClicked(this);
        }
    }

    /// <summary>
    /// 更新血条
    /// </summary>
    public void UpdateHealthSlider()
    {
        healthSlider.value = health;
    }
}