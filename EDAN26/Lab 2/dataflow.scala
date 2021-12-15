import scala.actors._
import java.util.BitSet;

// LAB 2: some case classes but you need additional ones too.

case class Start();
case class Stop();
case class Ready();
case class Go();
case class Change(in: BitSet);
case class Incrementvcounterx3000(num: Int);
case class Finished(num: Int);

class Random(seed: Int) {
        var w = seed + 1;
        var z = seed * seed + seed + 2;

        def nextInt() =
        {
                z = 36969 * (z & 65535) + (z >> 16);
                w = 18000 * (w & 65535) + (w >> 16);

                (z << 16) + w;
        }
}

class Controller(val cfg: Array[Vertex]) extends Actor {
  var started = 0;
  val begin   = System.currentTimeMillis();
  var vcounterx3000 = 0;

  // LAB 2: The controller must figure out when
  //        to terminate all actors somehow.

  def act() {
    react {
      case Ready() => {
        started += 1;
        // println("controller has seen " + started);
        if (started == cfg.length) {
          for (u <- cfg) {
                  vcounterx3000 += u.index;
                 // println(vcounterx3000);
                  u ! new Go;
          }
        }
        act();
      }
      case Incrementvcounterx3000(num: Int) =>  {

              vcounterx3000 += num;
              act();
      }
      case Finished(num: Int)  => {
        //      println("Vi kör en finished: " + vcounterx3000);
              vcounterx3000 -= num;
              if(vcounterx3000 == 0) {
                      cfg.map(x => x ! new Stop());
                      println("Time : " + (System.currentTimeMillis()-begin)/1000.0);
                      cfg.map(_.print);
              } else {
                      act();
              }
      }
    }
  }
}

class Vertex(val index: Int, s: Int, val controller: Controller) extends Actor {
  var pred: List[Vertex] = List();
  var succ: List[Vertex] = List();
  val uses               = new BitSet(s);
  val defs               = new BitSet(s);
  var in                 = new BitSet(s);
  var out                = new BitSet(s);

  def connect(that: Vertex)
  {
    //println(this.index + "->" + that.index);
    this.succ = that :: this.succ;
    that.pred = this :: that.pred;
  }

  def act() {
    react {
      case Start() => {
        controller ! new Ready;
        //println("started " + index);
        act();
      }

      case Go() => {
         // LAB 2: Start working with this vertex.
         succ.map(x => out.or(x.in));

         var newIn: BitSet = new BitSet();
         newIn.or(out);
         newIn.andNot(defs);
         newIn.or(uses);
         //printSet("new in ", index,  newIn);


         if(!in.equals(newIn)){
           in = newIn;
           pred.map(x => x ! new Change(in));
         }
         controller ! new Finished(index);

         act();
       }
       case Change(in: BitSet) => {
           controller ! new Incrementvcounterx3000(index);
           out.or(in);
           var newIn = new BitSet();
           newIn.or(out);
           newIn.andNot(defs);
           newIn.or(uses);

           if(!this.in.equals(newIn)){
             this.in = newIn;
             pred.map(x => x ! new Change(this.in));
           }
           controller ! new Finished(index);

           act();

         }

      case Stop()  => { }
    }
  }

  def printSet(name: String, index: Int, set: BitSet) {
    System.out.print(name + "[" + index + "] = { ");
    for (i <- 0 until set.size)
      if (set.get(i))
        System.out.print("" + i + " ");
    println("}");
  }

  def print = {
    printSet("use", index, uses);
    printSet("def", index, defs);
    printSet("in", index, in);
    printSet("out", index, out);
    println("");
  }
}

object Driver {
  val rand    = new Random(1);
  var nactive = 0;
  var nsym    = 0;
  var nvertex = 0;
  var maxsucc = 0;

  def makeCFG(cfg: Array[Vertex]) {

    cfg(0).connect(cfg(1));
    cfg(0).connect(cfg(2));

    for (i <- 2 until cfg.length) {
      val p = cfg(i);
      val s = (rand.nextInt() % maxsucc) + 1;

      for (j <- 0 until s) {
        val k = cfg((rand.nextInt() % cfg.length).abs);
        p.connect(k);
      }
    }
  }

  def makeUseDef(cfg: Array[Vertex]) {
    for (i <- 0 until cfg.length) {
      for (j <- 0 until nactive) {
        val s = (rand.nextInt() % nsym).abs;
        if (j % 4 != 0) {
          if (!cfg(i).defs.get(s))
            cfg(i).uses.set(s);
        } else {
          if (!cfg(i).uses.get(s))
            cfg(i).defs.set(s);
        }
      }
    }
  }

  def main(args: Array[String]) {
    nsym           = args(0).toInt;
    nvertex        = args(1).toInt;
    maxsucc        = args(2).toInt;
    nactive        = args(3).toInt;
    val print      = args(4).toInt;
    val cfg        = new Array[Vertex](nvertex);
    val controller = new Controller(cfg);

    controller.start;

    println("generating CFG...");
    for (i <- 0 until nvertex)
      cfg(i) = new Vertex(i, nsym, controller);

    makeCFG(cfg);
    println("generating usedefs...");
    makeUseDef(cfg);

    println("starting " + nvertex + " actors...");

    for (i <- 0 until nvertex)
      cfg(i).start;

    for (i <- 0 until nvertex)
      cfg(i) ! new Start;

  //   if (print != 0)
  //     for (i <- 0 until nvertex)
  //       cfg(i).print;
   }
}
