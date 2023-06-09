/*
There is one spaceship. X and Y co-odinate of source of spaceship and destination spaceship is given.
There are N number of warmholes; each warmhole has 5 values. First 2 values are starting co-ordinate
of warmhole and after that value no. 3 and 4 represents ending co-ordinate of warmhole and last 5th 
value is represents cost to pass through this warmhole. Now these warmholes are bi-directional. Now 
the to go from (x1,y1) to (x2,y2) is abs(x1-x2)+abs(y1-y2). The main problem here is to find minimum 
distance to reach spaceship from source to destination co-ordinate using any number of warm-hole. 
It is ok if you wont use any warmhole.
*/

public class WormHoleSpacehip{
  private int result=Integer.MAX_VALUE;
  public static class Vertex{
    public int x;
    public int y;
    public int index;
    public String type;
    
    public Vertex(int x,int y,int index,String type){
      this.x=x;
      this.y=y;
      this.index=index;
      this.type=type;
    }
  }
  
  public int getDistance(Vertex start,Vertex end){
    return (Mtah.abs(end.x-start.x)+Math.abs(end.y-start.y));
  }
  
  public void createGraphMatrixAndMinPath(int[] source,int[] destination,int[][] wormholes,int N){
    Vertex[] vertexList=new Vertex[wormholes.length*2+2]
    int[][] graph=new int[vertexList.length][vertexList.length];
    Vertex vertexsource =new Vertex(source[0],source[1],0,"source");
    vertexList[0]=vertexsource;
    int graphindex=1;
    for(int[] wormhole:wormholes){
      Vertex wormholestart=new Vertex(wormhole[0],wormhole[1],graphindex,"wormholestart"+graphindex);
      Vertex wormholeend=new Vertex(wormhole[2],wormhole[3],graphindex+1,"wormholeend"+(graphindex+1));
      int time=wormhole[4];
      vertexList[graphindex]=wormholestart;
      vertexList[graphindex+1]=wormholeend;
      graph[graphindex][graphindex+1]=graph[graphindex+1][graphindex]=time;
      graphindex+=2;
    }
    Vertex vertexdest =new Vertex(destination[0],destination[1],graphindex,"destination");
    vertexList[graphindex]=vertexdest;
    
    for(int i=0;i<graph.length;i++){
      graph[0][i]=getDistance(vertexList[0],vertexList[i]);
      graph[i][graph.length-1]=getDistance(vertexList[i],vertexList[graph.length-1]);
    }
    
    for(int i=1;i<graph.length;i++){
      for(int j=0;j<graph.length;j++){
        if(i==j){
          graph[i][j]=0;
        }else{
          if(i%2==1){
            graph[i][j]=graph[i][i+1]+getDistance(vertexList[i+1],vertexList[j]);
          }else{
            graph[i][j]=graph[i][i-1]+getDistance(vertexList[i-1],vertexList[j]);
          }
        }
      }
    }
    //Minimum path in graph
    boolean[] visited=new boolean[graph.length];
    minPath(graph,visited,vertexList[0],vertexList,0);
  }
  
  //DFS search with min distance in all paths
  public void minPath(int[][] graph,boolean[] visited,Vertex current,Vertex[] vertexes,int time){
    if(current==vertexes[vertexes.length-1]){
      result=Math.min(result,time);
      return;
    }
    
    if(visited[current.index]){
      return;
    }
    
    visited[current.index]=true;
    for(int i=0;i<graph.length;i++){
      minPath(graph,visited,vertexList[i],vertexList,time+graph[current.index][i]);
    }
    visited[current.index]=false;
  }
  
  	public static void main(String[] args) {
		int[] source= {0,0};
		int[] dest= {100,100};
		int[][] wormholes= {{50,50,100,100,2},{75,75,100,100,1}};
		creatingGraphOfVertexAsAdjancyMatrixAndMinPaththroughWormholes(source,dest,wormholes);
	}
  
}
