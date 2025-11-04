using System.Collections.Generic;
using TMPro;
using UnityEditor.PackageManager;
using UnityEngine;
using UnityEngine.UI;

public class CombinationChecker : MonoBehaviour
{

    // 检查骰子组合对应的技能
    public Skill CheckCombination(List<int> dices)
    {
        Skill currentSkill;
        // TEST_SKILL1
        if (dices.Count == 2 && dices[0] == dices[1])
        {
            currentSkill = Skill.TEST_SKILL1;
        }
        // TEST_SKILL2
        else if (dices.Count == 3 && dices[0] == dices[1] && dices[1] == dices[2])
        {
            currentSkill = Skill.TEST_SKILL2;
        }
        // NULL
        else
        {
            currentSkill = Skill.NULL;
        }

        return currentSkill;
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
