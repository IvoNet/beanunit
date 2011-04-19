/*
 * Copyright 2011 Ivo Woltring
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package immutable;

import java.math.BigDecimal;

/**
 * Holds information about the inventory
 */
public final class InventoryDto {
    private final BigDecimal insuredAmount;
    private final BigDecimal newWorth;

    /**
     * @param insuredAmount {@link java.math.BigDecimal} the current insured amount
     * @param newWorth      {@link java.math.BigDecimal} the current amount of new worth
     */
    public InventoryDto(final BigDecimal insuredAmount, final BigDecimal newWorth) {
        this.insuredAmount = insuredAmount;
        this.newWorth = newWorth;
    }

    public InventoryDto(final BigDecimal newWorth) {
        this(new BigDecimal(42), newWorth);
    }

    /**
     * @return {@link java.math.BigDecimal} the current insured amount
     */
    public BigDecimal getInsuredAmount() {
        return this.insuredAmount;
    }

    /**
     * @return {@link java.math.BigDecimal} the current amount of new worth
     */
    public BigDecimal getNewWorth() {
        return this.newWorth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof InventoryDto)) {
            return false;
        }

        final InventoryDto other = (InventoryDto) obj;

        return isEqual(this.insuredAmount, other.insuredAmount) && isEqual(this.newWorth, other.newWorth);
    }

    private boolean isEqual(final Object thisParam, final Object otherParam) {
        return (thisParam == null && otherParam == null) || (thisParam != null && thisParam.equals(otherParam));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 19;
        int result = prime;
        if (this.insuredAmount != null) {
            result *= prime + this.insuredAmount.hashCode();
        }
        if (this.newWorth != null) {
            result *= prime + this.newWorth.hashCode();
        }
        return result;
    }

}
