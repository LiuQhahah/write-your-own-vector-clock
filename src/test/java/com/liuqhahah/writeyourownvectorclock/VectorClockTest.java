package com.liuqhahah.writeyourownvectorclock;


import org.junit.jupiter.api.Test;

public class VectorClockTest {

  @Test
  public void multipleClocks() {
    VectorClock a = VectorClock.init("a");
    VectorClock b = VectorClock.init("b");
    VectorClock c = VectorClock.init("c");

    assert a.toString().equals("[a:0]");
    assert b.toString().equals("[b:0]");
    assert c.toString().equals("[c:0]");

    c = c.nextClock();
    b = b.add(c);



    assert a.toString().equals("[a:0]");
    assert b.toString().equals("[b:1,c:1]");
    assert c.toString().equals("[c:1]");


    b = b.nextClock();
    a = a.add(b);


    assert a.toString().equals("[a:1,b:2,c:1]");
    assert b.toString().equals("[b:2,c:1]");
    assert c.toString().equals("[c:1]");


    b = b.nextClock();
    c = c.add(b);

    assert a.toString().equals("[a:1,b:2,c:1]");
    assert b.toString().equals("[b:3,c:1]");
    assert c.toString().equals("[b:3,c:2]");


    a =  a.nextClock();
    b = b.add(a);

    assert a.toString().equals("[a:2,b:2,c:1]");
    assert b.toString().equals("[a:2,b:4,c:1]");
    assert c.toString().equals("[b:3,c:2]");

    c = c.nextClock();
    a = a.add(c);

    assert a.toString().equals("[a:3,b:3,c:3]");
    assert b.toString().equals("[a:2,b:4,c:1]");
    assert c.toString().equals("[b:3,c:3]");

    b = b.nextClock();
    c = c.add(b);

    assert a.toString().equals("[a:3,b:3,c:3]");
    assert b.toString().equals("[a:2,b:5,c:1]");
    assert c.toString().equals("[a:2,b:5,c:4]");

    c = c.nextClock();
    a = a.add(c);

    assert a.toString().equals("[a:4,b:5,c:5]");
    assert b.toString().equals("[a:2,b:5,c:1]");
    assert c.toString().equals("[a:2,b:5,c:5]");





  }

  @Test

  public void oneClock(){
    final StringNode a = StringNode.newStringNode("a");
    final StringNode b = StringNode.newStringNode("b");
    final StringNode c = StringNode.newStringNode("c");


    VectorClock aClock = VectorClock.init(a);
    VectorClock bClock = VectorClock.init(b);
    VectorClock cClock = VectorClock.init(c);

  }
}
