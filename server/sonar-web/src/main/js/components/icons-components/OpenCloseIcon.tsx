/*
 * SonarQube
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
import * as React from 'react';

interface Props {
  className?: string;
  open: boolean;
  size?: number;
}

export default function OpenCloseIcon({ className, open, size = 10 }: Props) {
  /* eslint-disable max-len */
  return (
    <svg
      className={className}
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 16 16"
      width={size}
      height={size}
      style={{ fill: 'currentColor', paddingTop: '2px' }}>
      {open ? (
        <path d="M15.027 7.335l-6.625 6.616q-0.17 0.17-0.402 0.17t-0.402-0.17l-6.625-6.616q-0.17-0.17-0.17-0.406t0.17-0.406l1.482-1.473q0.17-0.17 0.402-0.17t0.402 0.17l4.741 4.741 4.741-4.741q0.17-0.17 0.402-0.17t0.402 0.17l1.482 1.473q0.17 0.17 0.17 0.406t-0.17 0.406z" />
      ) : (
        <path d="M12.455 8.402l-6.625 6.625q-0.17 0.17-0.402 0.17t-0.402-0.17l-1.482-1.482q-0.17-0.17-0.17-0.402t0.17-0.402l4.741-4.741-4.741-4.741q-0.17-0.17-0.17-0.402t0.17-0.402l1.482-1.482q0.17-0.17 0.402-0.17t0.402 0.17l6.625 6.625q0.17 0.17 0.17 0.402t-0.17 0.402z" />
      )}
    </svg>
  );
}
