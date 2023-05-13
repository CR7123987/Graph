//Given a graph and a source vertex in the graph, find the shortest paths from the source to all vertices in the given graph.
import java.util.*;

public class Dijkstra{
  private List<Node> nodeList=new ArrayList<>();
  public Dijkstra(List<Node> nodeList){
    this.nodeList=nodeList;
  }
  
  public static class Node implements Comparable<Node>{
    public String name;
    public int index;
    public List<Node> neighbour= new ArrayList<>();
    public int distance;
    public HashMap<Node,Integer> nmap=new HashMap<>();
    public Node parent;
    public boolean isVisited=false;
    
    public Node(String name,int index){
      this.name=name;
      this.index=index;
    }
    
    public String toString(){
      return name+":"+index;
    }
    
    @Override
    public int compareTo(Node node){
      return this.distance-node.distance;
    }
    
  }
  
  public void addNeighbour(int i,int j,int dist){
    Node first=nodeList.get(i-1);
    Node second=nodeList.get(j-1);
    first.neighbour.add(second);
    first.nmap.put(second,dist);
  }
  
  public void dijkstra(Node source){
    PriorityQueue<Node> queue= new PriorityQueue<>();
    source.distance=0;
    queue.addAll(nodeList);
    
    while(!queue.isEmpty()){
      Node temp=queue.poll();
      temp.isVisited=true;
      for(Node neighbrnode:temp.neighbour){
        if(queue.contains(neighbrnode)){
          if(temp.distance+temp.nmap.get(neighbrnode)<neighbrnode.distance){
            queue.remove(neighbrnode);
            neighbrnode.distance=temp.distance+temp.nmap.get(neighbrnode);
            neighbrnode.parant=temp;
            queue.add(neighbrnode);
          }
        }
      }
    }
    
    //printing path from source to all nodes with distance
    for(Node node:nodeList){
      System.out.print("Node="+node+" has distance="+node.distance+" from="+source+" path=");
      printPath(node);
      System.out.println();
    }
  }
  
  public void printPath(Node node){
    if(node.parant!=null){
        printPath(node.parant);
    }
    System.out.print(node+"->");
  }
  
  
  
  
  
  
  public static void main(String[] args){
    List<Node> nodeList= new ArrayList<>();
    for(int i=0;i<5;i++){
      Node node=new Node("V:"+i+1,i);
    }
    
    Dijkstra djkst= new Dijkstra(nodeList);
		djkst.addNeighbour(1, 3, 6);
		djkst.addNeighbour(1, 4, 6);
		djkst.addNeighbour(2, 1, 3);
		djkst.addNeighbour(3, 4, 2);
		djkst.addNeighbour(4, 3, 1);
		djkst.addNeighbour(4, 2, 1);
		djkst.addNeighbour(5, 2, 4);
		djkst.addNeighbour(5, 4, 2);
  }
}
