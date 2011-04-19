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

import builder.AddressDto;

/**
 * Immutable Data transfer object for business location.
 */
public final class BusinessLocationDto {
    private final AddressDto address;
    private final boolean inventory;
    private final boolean stock;
    private final boolean stagnation;

    /**
     * @param address    {@link AddressDto}
     * @param inventory  boolean
     * @param stagnation boolean
     * @param stock      boolean
     */
    public BusinessLocationDto(final AddressDto address, final boolean inventory, final boolean stagnation,
                               final boolean stock) {
        this.address = address;
        this.inventory = inventory;
        this.stagnation = stagnation;
        this.stock = stock;
    }

    public AddressDto getAddress() {
        return this.address;
    }

    /**
     * @return true if it is inventory else false
     */
    public boolean isInventory() {
        return this.inventory;
    }

    /**
     * @return true of stock else false
     */
    public boolean isStock() {
        return this.stock;
    }

    /**
     * @return tue if stagnation else false
     */
    public boolean isStagnation() {
        return this.stagnation;
    }
}
