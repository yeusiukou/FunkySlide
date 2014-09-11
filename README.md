FunkySlide
==========

### The game description

FunkySlide is a simple puzzle game, where you have a 4x4 board with 15 elements (Christmas presents). There are 3 types of presents:

 1. Good presents, which you through to the Santa's sack (beneath the board)
 2. Bad presents. You have get rid of them.
 3. Bombs, which help you to get rid of bad presents.

Also you've got snow piles which you can't move but can destroy with bombs. The rules are simple: save all good presents from bombs and put them to the sack. With every destroyed good present you lose one life, the same rule is about putting wrong presents to the sack. The game is over when you make 5 mistakes or put a bomb to the sack.
There are bonuses (exploding 4 and more bad elements at a time, sliding 4 and more elements to the sack). The game has an online leaderboard in Google Play Games, so players can share their results with friends.

### Screenshots

<div id="pics" style=display:inline>
<img src="https://lh3.ggpht.com/RhVqP01T5yviyvPIlQFmA6Uea_X4bCQ_dUCFdcVgrJtvs6p65hm_hm_YexHAhy6ez1D7=h310-rw">
<img src="https://lh5.ggpht.com/mOtI9rk1yTlT_HwJLARcVJ0WQT38_TvDOj3FPkdRQzzSO6aCSXTWadyRdI8s5bxAjLoI=h310-rw">
<img src="https://lh6.ggpht.com/tNvb2vko-LKtFiui3KThCKIPMXjRQlt5pPDGv5sOD8er3-sQDrZvHCtc9NcAARLSog=h310-rw">
</div>

### Some creation details

The game was written on Java using the [LibGDX library](https://github.com/libgdx/libgdx) (Thanks to Mario Zechner and all who made this amazing piece of software). I did it as an additional project for the Java class in my college. Despite the simplicity of the game process I spent quite a time to write this app. The most challenging part for me was to write the presents movement algorithm. I hadn’t used any ready physics engine (like Box2D), so I had to take over of all these pushing, dragging and collisions of the board elements. Making the gameplay smooth and natural turned out not so easy as I firstly expected.

Even though I added the code for desktops, all fun is when you play it on a touchscreen.


----------
