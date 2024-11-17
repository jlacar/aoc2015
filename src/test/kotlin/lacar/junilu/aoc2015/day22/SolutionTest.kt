package lacar.junilu.aoc2015.day22

import lacar.junilu.aoc2015.day22.Spell.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SolutionTest {

    @Nested
    inner class Solution {
        @Test
        fun `Part 1 - `() {
            TODO()
//            assertEquals(0,
//                Simulator(
//                    Wizard(points = 50, mana = 500)
//                    Boss22(points = 58, damage = 9),
//                )
//                .cheapestWizardWin()
//            )
        }
    }

    @Nested
    inner class Spells {
        private val boss = Boss(points = 13, damage = 8)
        private val wizard = Wizard(points = 10, mana = 250)
        private val fight = Fight(wizard, boss)

        @Nested
        inner class `Magic Missile` {
            private val outcome = fight.cast(MAGIC_MISSILE)

            @Test
            fun `costs wizard 53 mana`() {
                assertEquals(53, wizard.mana - outcome.wizard.mana)
            }

            @Test
            fun `instantly does 4 damage to boss`() {
                assertEquals(4, boss.points - outcome.boss.points)
            }

            @Test
            fun `does not start a timed effect`() {
                assertFalse(outcome.hasActive(MAGIC_MISSILE))
            }

            @Test
            fun `fight so far costs 53 mana`() {
                assertEquals(53, outcome.cost())
            }
        }

        @Nested
        inner class Drain {
            private val outcome = fight.cast(DRAIN)

            @Test
            fun `costs wizard 73 mana`() {
                assertEquals(-73, outcome.wizard.mana - wizard.mana)
            }

            @Test
            fun `heals wizard for 2 hit points`() {
                assertEquals(2, outcome.wizard.points - wizard.points)
            }

            @Test
            fun `does not start a timed effect`() {
                assertFalse(outcome.hasActive(DRAIN))
            }
        }

        @Nested
        inner class Shield {
            private val afterCast = fight.cast(SHIELD)

            @Test
            fun `fight so far costs 113 mana`() {
                assertEquals(113, afterCast.cost())
            }

            @Test
            fun `costs wizard 113 mana`() {
                assertEquals(-113, afterCast.wizard.mana - wizard.mana)
            }

            @Test
            fun `starts an effect that lasts for 6 turns`() {
                assertTrue(afterCast.hasActive(SHIELD, 6))
            }

            @Test
            fun `does not affect boss when cast`() {
                assertEquals(boss, afterCast.boss)
            }

            @Nested
            inner class `When boss attacks`() {
                private val afterAttack = afterCast.attack()

                @Test
                fun `gives wizard 7 armor`() {
                    assertEquals(7, afterAttack.wizard.armor)
                }

                @Test
                fun `does not affect boss`() {
                    assertEquals(afterCast.boss, afterAttack.boss)
                }

                @Test
                fun `decreases its timer`() {
                    assertTrue(afterAttack.hasActive(SHIELD, 5))
                }
            }
        }

        @Nested
        inner class Poison {
            private val afterCast = fight.cast(POISON)

            @Test
            fun `costs wizard 173 mana`() {
                assertEquals(-173, afterCast.wizard.mana - wizard.mana)
            }

            @Test
            fun `starts an effect that lasts for 6 turns`() {
                assertTrue(afterCast.hasActive(POISON, 6))
            }

            @Test
            fun `does not immediately deal damage to boss when cast`() {
                assertEquals(boss, afterCast.boss)
            }

            @Nested
            inner class `When boss attacks`() {
                private val afterAttack = afterCast.attack()

                @Test
                fun `deals 3 damage to boss`() {
                    assertEquals(-3, afterAttack.boss.points - boss.points)
                }

                @Test
                fun `decreases timer to 5`() {
                    assertTrue(afterAttack.hasActive(POISON, 5))
                }
            }
        }

        @Nested
        inner class Recharge {
            private val afterCast = fight.cast(RECHARGE)

            @Test
            fun `costs wizard 229 mana`() {
                assertEquals(-229, afterCast.wizard.mana - wizard.mana)
            }

            @Test
            fun `starts an effect that lasts for 5 turns`() {
                assertTrue(afterCast.hasActive(RECHARGE, 5))
            }

            @Test
            fun `does not affect boss when cast`() {
                assertEquals(boss, afterCast.boss)
            }

            @Nested
            inner class `When boss attacks`() {
                private val afterAttack = afterCast.attack()

                @Test
                fun `wizard gains 101 mana`() {
                    assertEquals(101, afterAttack.wizard.mana - afterCast.wizard.mana)
                }

                @Test
                fun `wizard loses hit points`() {
                    assertEquals(afterCast.boss.damage, afterCast.wizard.points - afterAttack.wizard.points)
                }

                @Test
                fun `decreases timer to 4`() {
                    assertTrue(afterAttack.hasActive(RECHARGE, 4))
                }
            }
        }
    }

    @Nested
    inner class `Part 1 Example 1 scenarios` {

        val initialState = Fight(
            wizard = Wizard(points = 10, mana = 250),
            boss = Boss(points = 13, damage = 8)
        )
        val afterCast1 = initialState.cast(POISON)

        @Nested
        inner class `After wizard turn 1`() {

            @Test
            fun `starts poison effect for 6 turns`() {
                afterCast1.hasActive(POISON, 6)
            }

            @Test
            fun `wizard has 10 hit points, 77 mana, 0 armor`() {
                assertEquals(Wizard(points = 10, mana = 77), afterCast1.wizard)
            }

            @Test
            fun `boss not damaged yet`() {
                assertEquals(initialState.boss, afterCast1.boss)
            }
        }
    }
}