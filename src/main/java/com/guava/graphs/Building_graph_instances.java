package com.guava.graphs;

import com.google.common.graph.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by wuyongchong on 2018/11/5.
 * 构建图实例
 */
public class Building_graph_instances {

    MutableGraph<Integer> graph;
    MutableValueGraph<Object, Object> valueGraph;
    MutableNetwork<Object, Object> network;

    @Before
    public void test1(){
        //构建普通图-->无向图
        graph = GraphBuilder.undirected().build();

        //构建值图 <节点类型，边的值的类型>
        valueGraph = ValueGraphBuilder.directed().build();

        //构建网(两个节点之间支持多边，且每条边都是唯一的)
        network = NetworkBuilder.directed()
                .allowsParallelEdges(true)  //是否允许并行边
                .expectedNodeCount(1000)    //预期节点数
                .expectedEdgeCount(10000)   //预期边数
                .build();

        //默认创建的都是可变的图类型
        //根据现有的类型创建不可变的图类型
        //ImmutableGraph<Object> immutableGraph = ImmutableGraph.copyOf(graph);
    }

    //可变图与不可变图
    @Test
    public void test2(){
        /*******************可变图***********/
        //每个图形类型都有相应的Mutable*子类型:
        //MutableGraph、MutableValueGraph和MutableNetwork。
        //这些子类型定义了突变方法:
        //添加和删除节点的方法:addNode(node) and removeNode(node)
        //添加和删除边的方法

        /*******************不可变图*********/
        //一旦构建好，它们就不能被修改，并且在内部使用高效的不可变数据结构
        //通过调用其静态copyOf()方法，可以创建ImmutableGraph等的实例
        //ImmutableGraph<Integer> immutableGraph = ImmutableGraph.copyOf(graph);

        //图的节点必须可以被当做映射键 必须实现hashCode()和equals()
    }

    //行为
    @Test
    public void test3(){
        //1、突变
        graph.putEdge(1, 2);
        System.out.println(graph.nodes().contains(1));

        //2、图的equals和图等价

        //3、存取方法

        //4、同步：Mutable不保证线程安全

        //5、添加到图形中的节点、边缘和值对象与内置实现无关;它们只是用作内部数据结构的键。
        // 这意味着节点/边可以在图实例之间共享。

        //默认情况下，节点和边缘对象是按插入顺序排列的
        //(也就是说，节点()和边缘()的迭代器访问它们的顺序与LinkedHashSet一样)。

        //graph只是建模和一下基本方法 没有内置高级算法如迪杰斯特拉和弗洛伊德算法等
    }
}
