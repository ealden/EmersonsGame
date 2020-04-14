package gradle.cucumber;

import com.escanan.ealden.race.model.Racer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RacerSteps {
    private class TestableRacer extends Racer {
        void setDamage(int damage) {
            this.damage = damage;
        }

        void setPosition(int position) {
            this.position = position;
        }

        void setFinishLine(int finishLine) {
            this.finishLine = finishLine;
        }
    }

    private TestableRacer racer = null;
    private Racer.SpeedType speedType = null;

    @Given("I am in a race")
    public void newGame() {
        racer = new TestableRacer();
    }

    @Given("I am at position {int}")
    public void setPosition(int position) {
        racer.setPosition(position);
    }

    @Given("I have damage of {int}")
    public void setDamage(int damage) {
        racer.setDamage(damage);
    }

    @Given("I see the finish line at position {int}")
    public void setFinishLine(int finishLine) {
        racer.setFinishLine(finishLine);
    }

    @When("I choose {string} speed")
    public void speed(String speed) {
        speedType = Racer.SpeedType.valueOf(speed);
    }

    @When("I roll a {int}")
    public void roll(int roll) {
        racer.move(roll, speedType);
    }

    @Then("I must now be at position {int}")
    public void assertNewPosition(int newPosition) {
        assertThat(racer.getPosition(), is(equalTo(newPosition)));
    }

    @Then("I must now have damage of {int}")
    public void assertNewDamage(int newDamage) {
        assertThat(racer.getDamage(), is(equalTo(newDamage)));
    }

    @Then("I must see the race result: {string}")
    public void assertResult(String result) {
        var over = "WIN".equals(result);

        assertThat(racer.isOver(), is(over));
    }
}
