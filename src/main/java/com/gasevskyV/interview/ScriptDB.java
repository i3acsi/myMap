package com.gasevskyV.interview;

import java.util.*;
import java.util.stream.Collectors;

/*
Допустим есть база данных скриптов.
Каждый скрипт имеет произвольное количество зависимостей.
Зависимости выражаются в виде списка scriptIds, которые должны быть выполнены перед заданным сценарием.
Циклические зависимости отсутствуют.
Придумать алгоритм, чтобы запускать все сценарии в разумном порядке.
 */
public class ScriptDB {
    private Map<Integer, List<Integer>> map;
    private List<Integer> result = new ArrayList<>();
    Queue<Integer> data = new LinkedList<>();

    public List<Integer> order(List<VulnerabilityScript> list) {
        /*
        Для начала получу все узлы
        */
        Set<Integer> links = new HashSet<>();
        for (VulnerabilityScript scr : list) {
            links.addAll(scr.getDependencies());
        }

        /*
        Получу лист id
         */
        List<Integer> ids = list.stream().map(VulnerabilityScript::getScriptId).collect(Collectors.toList());

        /*
        Корень
         */
        Integer root = null; //корень
        for (int i : ids) {
            if (!links.remove(i)) {
                root = i;
                break;
            }
        }

        /*
        инициализирую мапу
         */
        this.map = list.stream().collect(Collectors.toMap(
                VulnerabilityScript::getScriptId,
                VulnerabilityScript::getDependencies));
        /*
        обход дерева без рекурсии
         */
        LinkedList<Integer> data = new LinkedList<>();
        data.offer(root);
        while (!data.isEmpty()) {
            Integer el = data.getLast();
            List<Integer> list1 = map.remove(el);
            if (list1 == null) {
                result.add(data.removeLast());
            } else {
                for (Integer i : list1) {
                    data.offer(i);
                }
            }
        }

        /*
int count =0;
        Optional<Integer> rsl = Optional.empty();
        LinkedList<Integer> data = new LinkedList<>();
        data.offer(root);
        while (!data.isEmpty()) {
            Integer el = data.poll();
            List<Integer> list1 = map.remove(el);
            if (list1 != null) {
                for(Integer i :list1){
                    data.offer(i);
                }
            }
            System.out.println(el);
        }
         */

//        leaves(root);
//        return this.result;
        return result;
    }

    private void leaves(Integer link) {
        var temp = map.get(link);
        if (temp != null) {
            for (Integer i : temp) {
                leaves(i);
            }
            result.add(link);
        } else {
            result.add(link);
        }
    }
}