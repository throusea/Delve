using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class SkillController : MonoBehaviour
{
    Skill currentSkill = Skill.NULL;

    private Button button; // 待弃用

    /// <summary>
    /// 释放技能
    /// </summary>
    public void ExecuteSkill()
    {
        if (currentSkill != Skill.NULL)
            Debug.Log("释放 " + currentSkill + "!");
        else
        {
            Debug.Log("无效技能！");
        }
    }

    public void SetAvailiableSkill(Skill skill)
    {
        this.currentSkill = skill;
    }

    /// <summary>
    /// 更新绑定按钮的文本为技能名称
    /// </summary>
    public void UpdateButtonText()
    {
        if (currentSkill == Skill.TEST_SKILL1)
        {
            button.enabled = true;
            button.GetComponentInChildren<TextMeshProUGUI>().text = "技能1";
        }

        else if (currentSkill == Skill.TEST_SKILL2)
        {
            button.enabled = true;
            button.GetComponentInChildren<TextMeshProUGUI>().text = "技能2";
        }

        else
        {
            button.enabled = false;
            button.GetComponentInChildren<TextMeshProUGUI>().text = "无效技能";
        }
    }

    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
