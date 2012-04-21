import scala.io.Source
/**
 * class to build sql inserts statements
 */
object SQLGenerator {
  
  def main(args: Array[String]) {
    getStars
	getDirectors
  }
  
  def parseLine(line:String):(String,Int) =  {
    //remove punctuation
    val arr:Array[String] = line.replace("(","").replace(")", "").replaceAll("\"", "").split(", ")
    //return tuple of name and rep
    (arr(0), arr(1).toInt)
  }
    
  //showing off
  def processLines(func:(String)=>(String,Int))(filename:String, sql:String){
	val source = scala.io.Source.fromFile(filename)
	val lines = source.getLines()
	lines.foreach{x => val(a,b) = func(x) 
	    			writeSQL(sql, a, b)}
	source.close ()
  }
  
  def getStars = processLines(parseLine)("stars.txt", "<statement>insert into actor (actor_name, actor_hire_cost, actor_reputation) values ('%s', %d, %d)</statement>")
  def getDirectors = processLines(parseLine)("directors.txt", "<statement>insert into director (director_name, director_hire_cost, director_reputation) values ('%s', %d, %d)</statement>")
 
  def writeSQL(sql:String, name:String, rep:Int){
   val s = sql.format(name, calcPrice(rep) ,rep)
   println(s)
  }
  
  //fiendishly complex algorithm
  def calcPrice(rep:Int):Int = {
    val cost = (rep / 4).toInt
	if(cost < 1) 1
	else cost
	
  }
}