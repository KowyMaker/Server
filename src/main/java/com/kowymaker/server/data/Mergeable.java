/**
 * This file is part of Kowy Maker.
 * 
 * Kowy Maker is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Kowy Maker is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Kowy Maker. If not, see <http://www.gnu.org/licenses/>.
 */
package com.kowymaker.server.data;

public interface Mergeable<T>
{
    public void merge(T data);
}
